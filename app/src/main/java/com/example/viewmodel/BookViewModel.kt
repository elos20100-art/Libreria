package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Book
import com.example.data.BookRepository
import com.example.data.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface ReadingTheme {
    val bg: Long
    val text: Long

    object Cream : ReadingTheme {
        override val bg = 0xFFFDFBF7
        override val text = 0xFF2D1E10
    }
    object Night : ReadingTheme {
        override val bg = 0xFF0F172A
        override val text = 0xFFE2E8F0
    }
    object Sepia : ReadingTheme {
        override val bg = 0xFFF4ECD8
        override val text = 0xFF433422
    }
    object Sage : ReadingTheme {
        override val bg = 0xFFF1F5F0
        override val text = 0xFF1C2D21
    }
}

data class ReadingSettings(
    val fontSizeMultiplier: Float = 1.0f, // 1.0f is normal, 1.2f is large...
    val theme: ReadingTheme = ReadingTheme.Cream
)

data class BookProgress(
    val bookId: String,
    val currentChapterIndex: Int = 0,
    val currentParagraphIndex: Int = 0,
    val isFavorite: Boolean = false
)

data class AudioState(
    val activeBook: Book? = null,
    val isPlaying: Boolean = false,
    val currentChapterIdx: Int = 0,
    val currentParagraphIdx: Int = 0,
    val speed: Float = 1.0f,
    val pitch: Float = 1.0f,
    val isTtsReady: Boolean = false,
    val errorMsg: String? = null
)

class BookViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val sharedPrefs = application.getSharedPreferences("lectura_antigua_prefs", Context.MODE_PRIVATE)

    // App Settings State (Theme Mode and Reader Language)
    private val _themeMode = MutableStateFlow(sharedPrefs.getString("theme_mode_pref", "SISTEMA") ?: "SISTEMA")
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _language = MutableStateFlow(sharedPrefs.getString("language_pref", "ES") ?: "ES")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _use24Hour = MutableStateFlow(sharedPrefs.getBoolean("use_24h_pref", true))
    val use24Hour: StateFlow<Boolean> = _use24Hour.asStateFlow()

    // Books Library
    val books: List<Book>
        get() = BookRepository.getBooksForLanguage(_language.value)

    // Reading Settings State
    private val _settings = MutableStateFlow(ReadingSettings())
    val settings: StateFlow<ReadingSettings> = _settings.asStateFlow()

    // Bookmarking / Favorites States indexed by bookId
    private val _progressMap = MutableStateFlow<Map<String, BookProgress>>(emptyMap())
    val progressMap: StateFlow<Map<String, BookProgress>> = _progressMap.asStateFlow()

    // Audiobook TTS Active Player State
    private val _audioState = MutableStateFlow(AudioState())
    val audioState: StateFlow<AudioState> = _audioState.asStateFlow()

    private var tts: TextToSpeech? = null
    private val UTTERANCE_ID = "OfflineBookNarratorUtterance"

    init {
        // Initialize Text To Speech
        tts = TextToSpeech(application, this)
        loadSavedProgressData()
    }

    fun setThemeMode(mode: String) {
        sharedPrefs.edit().putString("theme_mode_pref", mode).apply()
        _themeMode.value = mode
    }

    fun setLanguage(lang: String) {
        sharedPrefs.edit().putString("language_pref", lang).apply()
        _language.value = lang
        applyTtsLanguage(lang)
        
        // Dynamic re-sync active audiobook if matching id
        val activeBookId = _audioState.value.activeBook?.id
        if (activeBookId != null) {
            val updatedBook = BookRepository.getBooksForLanguage(lang).firstOrNull { it.id == activeBookId }
            if (updatedBook != null) {
                _audioState.update { it.copy(activeBook = updatedBook) }
            }
        }
    }

    fun setUse24Hour(value: Boolean) {
        sharedPrefs.edit().putBoolean("use_24h_pref", value).apply()
        _use24Hour.value = value
    }

    private fun applyTtsLanguage(lang: String) {
        val locale = when (lang) {
            "EN" -> Locale.US
            "FR" -> Locale.FRANCE
            "PT" -> Locale("pt", "BR")
            else -> Locale("es", "ES")
        }
        try {
            val result = tts?.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.getDefault())
                Log.e("BookViewModel", "Language $lang not fully supported in offline TTS; fallback applied.")
            } else {
                Log.d("BookViewModel", "TTS Language successfully changed to $lang")
            }
        } catch (e: Exception) {
            Log.e("BookViewModel", "TTS language setting error: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val currentLang = _language.value
            val locale = when (currentLang) {
                "EN" -> Locale.US
                "FR" -> Locale.FRANCE
                "PT" -> Locale("pt", "BR")
                else -> Locale("es", "ES")
            }
            
            var result = tts?.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                val spainLocale = Locale("es", "ES")
                val defaultSpanish = Locale("es")
                result = tts?.setLanguage(spainLocale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    result = tts?.setLanguage(defaultSpanish)
                }
            }

            // Route audio attributes explicitly to USAGE_MEDIA so that volume is controlled by the Media slider (Music/Games)
            try {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
                tts?.setAudioAttributes(audioAttributes)
            } catch (e: Exception) {
                Log.e("BookViewModel", "Could not set audio attributes for TTS: ${e.message}")
            }

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                val systemLocale = Locale.getDefault()
                tts?.setLanguage(systemLocale)
                _audioState.update { 
                    it.copy(
                        isTtsReady = true, 
                        errorMsg = "Idioma hispano no detectado offline. Usando voz del dispositivo."
                    ) 
                }
            } else {
                _audioState.update { 
                    it.copy(
                        isTtsReady = true, 
                        errorMsg = null
                    ) 
                }
            }
            setupTtsProgressListener()
        } else {
            Log.e("BookViewModel", "Failed to initialize TTS.")
            _audioState.update { it.copy(errorMsg = "No se pudo iniciar el servicio de voz") }
        }
    }

    private fun setupTtsProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                // UI shows active paragraph highlight
            }

            override fun onDone(utteranceId: String?) {
                // Background thread callback -> transition to Main
                viewModelScope.launch(Dispatchers.Main) {
                    if (_audioState.value.isPlaying) {
                        advanceAudiobook()
                    }
                }
            }

            override fun onError(utteranceId: String?) {
                Log.e("BookViewModel", "TTS Utterance Error")
            }
        })
    }

    // Load progress and favorites from SharedPreferences
    private fun loadSavedProgressData() {
        val loadedMap = mutableMapOf<String, BookProgress>()
        books.forEach { book ->
            val fav = sharedPrefs.getBoolean("${book.id}_fav", false)
            val chap = sharedPrefs.getInt("${book.id}_chap", 0)
            val para = sharedPrefs.getInt("${book.id}_para", 0)
            loadedMap[book.id] = BookProgress(
                bookId = book.id,
                currentChapterIndex = chap,
                currentParagraphIndex = para,
                isFavorite = fav
            )
        }
        _progressMap.value = loadedMap
    }

    // Toggle Book as Favorite
    fun toggleFavorite(bookId: String) {
        val currentProgress = _progressMap.value[bookId] ?: BookProgress(bookId = bookId)
        val newFav = !currentProgress.isFavorite
        sharedPrefs.edit().putBoolean("${bookId}_fav", newFav).apply()
        
        _progressMap.update { map ->
            map + (bookId to currentProgress.copy(isFavorite = newFav))
        }
    }

    // Update read progression
    fun saveReadingProgress(bookId: String, chapterIdx: Int, paragraphIdx: Int) {
        val currentProgress = _progressMap.value[bookId] ?: BookProgress(bookId = bookId)
        sharedPrefs.edit()
            .putInt("${bookId}_chap", chapterIdx)
            .putInt("${bookId}_para", paragraphIdx)
            .apply()

        _progressMap.update { map ->
            map + (bookId to currentProgress.copy(
                currentChapterIndex = chapterIdx,
                currentParagraphIndex = paragraphIdx
            ))
        }
    }

    // Set layout/reader colors
    fun setReadingTheme(theme: ReadingTheme) {
        _settings.update { it.copy(theme = theme) }
    }

    // Increase / Decrease base text size
    fun setFontSizeMultiplier(multiplier: Float) {
        _settings.update { it.copy(fontSizeMultiplier = multiplier.coerceIn(0.7f, 2.0f)) }
    }

    // === Audiobook Controls ===

    fun selectAudiobook(book: Book) {
        val saved = _progressMap.value[book.id] ?: BookProgress(bookId = book.id)
        
        // Stop current TTS playing if any
        stopAudiobookSpeech()

        _audioState.update {
            AudioState(
                activeBook = book,
                isPlaying = false,
                currentChapterIdx = saved.currentChapterIndex,
                currentParagraphIdx = saved.currentParagraphIndex,
                speed = it.speed,
                pitch = it.pitch,
                isTtsReady = it.isTtsReady,
                errorMsg = it.errorMsg
            )
        }
    }

    fun playAudiobook() {
        val state = _audioState.value
        val book = state.activeBook ?: return
        if (!state.isTtsReady) {
            _audioState.update { it.copy(errorMsg = "Servicio de voz no listo.") }
            return
        }

        _audioState.update { it.copy(isPlaying = true, errorMsg = null) }
        speakCurrentParagraph()
    }

    fun pauseAudiobook() {
        tts?.stop()
        _audioState.update { it.copy(isPlaying = false) }
    }

    fun stopAudiobook() {
        stopAudiobookSpeech()
    }

    private fun stopAudiobookSpeech() {
        tts?.stop()
        _audioState.update { it.copy(isPlaying = false) }
    }

    fun setPlaybackSpeed(newSpeed: Float) {
        _audioState.update { it.copy(speed = newSpeed) }
        tts?.setSpeechRate(newSpeed)
        if (_audioState.value.isPlaying) {
            // Re-speak to apply speed change immediately
            speakCurrentParagraph()
        }
    }

    fun seekAudiobookChapter(chapterIndex: Int) {
        val state = _audioState.value
        val book = state.activeBook ?: return
        val validChapterIdx = chapterIndex.coerceIn(0, book.chapters.lastIndex)
        
        _audioState.update {
            it.copy(
                currentChapterIdx = validChapterIdx,
                currentParagraphIdx = 0
            )
        }
        
        // Save progress synchronized with reader
        saveReadingProgress(book.id, validChapterIdx, 0)

        if (_audioState.value.isPlaying) {
            speakCurrentParagraph()
        }
    }

    fun skipForward() {
        val state = _audioState.value
        val book = state.activeBook ?: return
        val currentChap = book.chapters[state.currentChapterIdx]

        if (state.currentParagraphIdx < currentChap.paragraphs.lastIndex) {
            // Next paragraph in same chapter
            _audioState.update { it.copy(currentParagraphIdx = state.currentParagraphIdx + 1) }
        } else if (state.currentChapterIdx < book.chapters.lastIndex) {
            // Next chapter
            _audioState.update {
                it.copy(
                    currentChapterIdx = state.currentChapterIdx + 1,
                    currentParagraphIdx = 0
                )
            }
        } else {
            // End of book
            stopAudiobookSpeech()
            return
        }

        saveReadingProgress(book.id, _audioState.value.currentChapterIdx, _audioState.value.currentParagraphIdx)
        if (_audioState.value.isPlaying) {
            speakCurrentParagraph()
        }
    }

    fun skipBackward() {
        val state = _audioState.value
        val book = state.activeBook ?: return

        if (state.currentParagraphIdx > 0) {
            // Prev paragraph
            _audioState.update { it.copy(currentParagraphIdx = state.currentParagraphIdx - 1) }
        } else if (state.currentChapterIdx > 0) {
            // Prev chapter's last paragraph
            val prevChapIdx = state.currentChapterIdx - 1
            val prevChap = book.chapters[prevChapIdx]
            _audioState.update {
                it.copy(
                    currentChapterIdx = prevChapIdx,
                    currentParagraphIdx = prevChap.paragraphs.lastIndex
                )
            }
        } else {
            // Already at beginning
            _audioState.update { it.copy(currentParagraphIdx = 0) }
        }

        saveReadingProgress(book.id, _audioState.value.currentChapterIdx, _audioState.value.currentParagraphIdx)
        if (_audioState.value.isPlaying) {
            speakCurrentParagraph()
        }
    }

    private fun speakCurrentParagraph() {
        val state = _audioState.value
        val book = state.activeBook ?: return
        
        if (state.currentChapterIdx >= book.chapters.size) return
        val chapter = book.chapters[state.currentChapterIdx]
        
        if (state.currentParagraphIdx >= chapter.paragraphs.size) return
        val paragraphText = chapter.paragraphs[state.currentParagraphIdx]

        // Adjust speed/pitch
        tts?.setSpeechRate(state.speed)
        tts?.setPitch(state.pitch)

        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTERANCE_ID)
        }

        val speakResult = tts?.speak(paragraphText, TextToSpeech.QUEUE_FLUSH, params, UTTERANCE_ID)
        if (speakResult == TextToSpeech.ERROR) {
            val errorMsg = "Atención: El motor de voz (TTS) falló al reproducirse. Esto ocurre porque el navegador o emulador carece de archivos de síntesis de voz locales. ¡Usa 'Probar Sonido' arriba o lee con el lector interactivo!"
            Log.e("BookViewModel", errorMsg)
            _audioState.update { it.copy(errorMsg = errorMsg, isPlaying = false) }
        } else {
            Log.d("BookViewModel", "Sintetizando párrafo ${state.currentParagraphIdx}: ${paragraphText.take(20)}...")
        }
    }

    private fun advanceAudiobook() {
        val state = _audioState.value
        val book = state.activeBook ?: return
        val chapter = book.chapters[state.currentChapterIdx]

        if (state.currentParagraphIdx < chapter.paragraphs.lastIndex) {
            // Next paragraph in this chapter
            val nextPara = state.currentParagraphIdx + 1
            _audioState.update { it.copy(currentParagraphIdx = nextPara) }
            saveReadingProgress(book.id, state.currentChapterIdx, nextPara)
            speakCurrentParagraph()
        } else if (state.currentChapterIdx < book.chapters.lastIndex) {
            // Next chapter
            val nextChap = state.currentChapterIdx + 1
            _audioState.update {
                it.copy(
                    currentChapterIdx = nextChap,
                    currentParagraphIdx = 0
                )
            }
            saveReadingProgress(book.id, nextChap, 0)
            speakCurrentParagraph()
        } else {
            // Finished book!
            _audioState.update { it.copy(isPlaying = false, currentParagraphIdx = 0, currentChapterIdx = 0) }
            saveReadingProgress(book.id, 0, 0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Critical cleanup to prevent memory leak and TTS audio continuing in background!
        tts?.stop()
        tts?.shutdown()
    }
}
