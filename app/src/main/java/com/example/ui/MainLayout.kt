package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Hearing
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Book
import com.example.data.Chapter
import com.example.viewmodel.BookViewModel
import com.example.viewmodel.ReadingTheme
import com.example.viewmodel.BookProgress
import com.example.viewmodel.AudioState
import com.example.viewmodel.Translator
import kotlinx.coroutines.launch
import kotlin.math.sin

// Screen routes / State Navigation
sealed interface Screen {
    object Library : Screen
    data class Reader(val bookId: String) : Screen
    data class Player(val bookId: String) : Screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLibraryApp(viewModel: BookViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Library) }
    
    // Animate transition between screens
    Crossfade(
        targetState = currentScreen,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "screen_tr"
    ) { screen ->
        when (screen) {
            is Screen.Library -> {
                LibraryLandingScreen(
                    viewModel = viewModel,
                    onOpenReader = { currentScreen = Screen.Reader(it) },
                    onOpenPlayer = { currentScreen = Screen.Player(it) }
                )
            }
            is Screen.Reader -> {
                BookReaderScreen(
                    bookId = screen.bookId,
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Library }
                )
            }
            is Screen.Player -> {
                AudiobookPlayerScreen(
                    bookId = screen.bookId,
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Library }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryLandingScreen(
    viewModel: BookViewModel,
    onOpenReader: (String) -> Unit,
    onOpenPlayer: (String) -> Unit
) {
    val progressMap by viewModel.progressMap.collectAsState()
    val audioState by viewModel.audioState.collectAsState()
    val currentLang by viewModel.language.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    
    // Live Clock / Time and Date
    var currentDateTimeString by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        while (true) {
            val now = java.util.Calendar.getInstance().time
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
            currentDateTimeString = sdf.format(now)
            kotlinx.coroutines.delay(1000)
        }
    }
    
    var selectedTab by remember { mutableStateOf(0) } // 0: Libros, 1: Audiolibros, 2: Favoritos
    val tabs = listOf(
        Translator.translate("menu_library", currentLang),
        Translator.translate("menu_audiobooks", currentLang),
        Translator.translate("menu_favorites", currentLang)
    )
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(320.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ) {
                // Header block with elegant gradient styling
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = Translator.translate("drawer_header_greeting", currentLang),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = Translator.translate("drawer_config_desc", currentLang),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Date & Time Live display Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendario",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Column {
                            Text(
                                text = Translator.translate("date_time_label", currentLang),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentDateTimeString,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title for Theme Setting
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = Translator.translate("theme_label", currentLang).uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        // Theme Switch Row Options
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                Triple("SISTEMA", Translator.translate("theme_system", currentLang), Icons.Default.Settings),
                                Triple("CLARO", Translator.translate("theme_light", currentLang), Icons.Default.LightMode),
                                Triple("OSCURO", Translator.translate("theme_dark", currentLang), Icons.Default.DarkMode)
                            ).forEach { (modeCode, titleStr, iconVec) ->
                                val active = themeMode == modeCode
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.setThemeMode(modeCode) }
                                        .testTag("theme_btn_$modeCode"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (active) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                                    ),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = iconVec,
                                            contentDescription = null,
                                            tint = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = titleStr,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                                            color = if (active) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                        RadioButton(
                                            selected = active,
                                            onClick = { viewModel.setThemeMode(modeCode) }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Title for Language Setting
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = Translator.translate("lang_label", currentLang).uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        // Languages Column List
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                Triple("ES", "Español", "🇪🇸"),
                                Triple("EN", "English", "🇺🇸"),
                                Triple("FR", "Français", "🇫🇷"),
                                Triple("PT", "Português", "🇧🇷")
                            ).forEach { (langCode, langName, flagChar) ->
                                val active = currentLang == langCode
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { viewModel.setLanguage(langCode) }
                                        .testTag("lang_btn_$langCode"),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (active) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                                    ),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = flagChar,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = langName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                                            color = if (active) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                        RadioButton(
                                            selected = active,
                                            onClick = { viewModel.setLanguage(langCode) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }
                            },
                            modifier = Modifier.testTag("hamburger_menu_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú hamburguesa",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(Color(0xFFEAB308), Color(0xFFD97706))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = "Logo",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = Translator.translate("app_title", currentLang),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = Translator.translate("app_subtitle", currentLang),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    )
                )
            },
            bottomBar = {
                // Underlay mini-player bar if an audiobook is active and playing in background
                Column {
                    if (audioState.activeBook != null) {
                        MiniPlayerBar(
                            audioState = audioState,
                            onPlayPause = {
                                if (audioState.isPlaying) viewModel.pauseAudiobook() else viewModel.playAudiobook()
                            },
                            onOpenFullPlayer = {
                                onOpenPlayer(audioState.activeBook!!.id)
                            }
                        )
                    }
                    
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                    ) {
                        tabs.forEachIndexed { index, label ->
                            val icon = when (index) {
                                0 -> Icons.Outlined.MenuBook
                                1 -> Icons.Outlined.Hearing
                                else -> Icons.Default.Favorite
                            }
                            NavigationBarItem(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                label = { Text(label, fontSize = 11.sp) },
                                icon = { Icon(icon, contentDescription = label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                            )
                        )
                    )
            ) {
                // Curated literary quote banner at top
                LiteraryQuoteBanner()

                val filteredBooks = when (selectedTab) {
                    0 -> viewModel.books // Display all books to read
                    1 -> viewModel.books // Display books available for audiobook
                    else -> viewModel.books.filter { progressMap[it.id]?.isFavorite == true } // Favorites
                }

                if (filteredBooks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = if (selectedTab == 2) Icons.Default.Favorite else Icons.Default.MenuBook,
                                contentDescription = "Sin libros",
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                modifier = Modifier.size(72.dp)
                            )
                            Text(
                                text = if (selectedTab == 2) 
                                    Translator.translate("no_favorites", currentLang) 
                                    else Translator.translate("empty_search", currentLang),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .testTag("book_list"),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(filteredBooks) { _, book ->
                            val prog = progressMap[book.id] ?: BookProgress(bookId = book.id)
                            BookCatalogCard(
                                book = book,
                                progress = prog,
                                isAudioTab = selectedTab == 1,
                                onReadClick = { onOpenReader(book.id) },
                                onListenClick = { 
                                    viewModel.selectAudiobook(book)
                                    onOpenPlayer(book.id)
                                },
                                onToggleFav = { viewModel.toggleFavorite(book.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LiteraryQuoteBanner() {
    val quotes = remember {
        listOf(
            "“El que lee mucho y anda mucho, ve mucho y sabe mucho.” — Miguel de Cervantes",
            "“Lo esencial es invisible para los ojos.” — Antoine de Saint-Exupéry",
            "“No dejes para mañana lo que puedas leer hoy.” — Biblioteca Popular",
            "“La literatura es siempre una expedición a la verdad.” — Franz Kafka",
            "“Para viajar lejos, no hay mejor nave que un libro.” — Emily Dickinson"
        )
    }
    // Simple state to rotate daily quote
    val quote = remember { quotes.random() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Estrella",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = quote,
                fontSize = 13.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BookCatalogCard(
    book: Book,
    progress: BookProgress,
    isAudioTab: Boolean,
    onReadClick: () -> Unit,
    onListenClick: () -> Unit,
    onToggleFav: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("book_card_${book.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Artistic Book Cover Widget using absolute Compose drawing
            Box(
                modifier = Modifier
                    .width(84.dp)
                    .height(116.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(book.coverBg))
                    .border(
                        BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Book Spine highlight gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.White.copy(alpha = 0.1f),
                                    Color.Transparent
                                )
                            )
                        )
                        .align(Alignment.CenterStart)
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = book.title,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 11.sp
                    )
                    
                    Text(
                        text = book.coverEmoji,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    
                    Text(
                        text = book.author.substringBefore(",").trim(),
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Info column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = book.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )
                    
                    // Quick Favorite Icon button
                    IconButton(
                        onClick = onToggleFav,
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("id_fav_${book.id}")
                    ) {
                        Icon(
                            imageVector = if (progress.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (progress.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "por ${book.author}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))
                
                // Book progress indices
                val totalChapters = book.chapters.size
                val currentChap = progress.currentChapterIndex
                val percent = if (totalChapters > 0) {
                    ((currentChap.toFloat() / totalChapters.toFloat()) * 100f).toInt().coerceIn(0, 100)
                } else 0

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QueryBuilder,
                        contentDescription = "Tiempo",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${book.estimatedMinutes} min | ${percent}% completado",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action buttons spacing
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Read Button with 48dp touch target
                    Button(
                        onClick = onReadClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp)
                            .testTag("read_btn_${book.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = "Leer", modifier = Modifier.size(14.dp))
                            Text("Leer", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Listen Button with 48dp touch target
                    Button(
                        onClick = onListenClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp)
                            .testTag("listen_btn_${book.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Escuchar", modifier = Modifier.size(14.dp))
                            Text("Escuchar", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiniPlayerBar(
    audioState: AudioState,
    onPlayPause: () -> Unit,
    onOpenFullPlayer: () -> Unit
) {
    val book = audioState.activeBook ?: return
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpenFullPlayer)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(book.coverBg), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(book.coverEmoji, fontSize = 20.sp)
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "REPRODUCIENDO AUDIOLIBRO",
                    fontSize = 8.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = book.title,
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Equalizer animation
            MiniEqualizerAnim(isPlaying = audioState.isPlaying)

            IconButton(
                onClick = onPlayPause,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Pausa_Play",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun MiniEqualizerAnim(isPlaying: Boolean) {
    val transition = rememberInfiniteTransition()
    val heights = (0..3).map { i ->
        if (isPlaying) {
            transition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 350 + (i * 120),
                        delayMillis = i * 50,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "eq_$i"
            )
        } else {
            remember { mutableStateOf(0.3f) }
        }
    }

    Row(
        modifier = Modifier
            .height(20.dp)
            .width(16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        heights.forEach { h ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(h.value)
                    .background(Color.White, RoundedCornerShape(1.dp))
            )
        }
    }
}

// === BOOK READER COMPOSABLE ===

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderScreen(
    bookId: String,
    viewModel: BookViewModel,
    onBack: () -> Unit
) {
    val book = viewModel.books.first { it.id == bookId }
    val progressMap by viewModel.progressMap.collectAsState()
    val settings by viewModel.settings.collectAsState()
    
    val currentSetting = settings.theme
    val containerBgColor = Color(currentSetting.bg)
    val textPrimaryColor = Color(currentSetting.text)
    
    val savedProg = progressMap[bookId] ?: BookProgress(bookId)
    var currentChapterIndex by remember { mutableStateOf(savedProg.currentChapterIndex.coerceIn(0, book.chapters.lastIndex)) }
    
    val chapter = book.chapters[currentChapterIndex]
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    // Bottom sheet controls for Text Settings
    var showSettingsSheet by remember { mutableStateOf(false) }

    // Register reading progress when chapter changes
    LaunchedEffect(currentChapterIndex) {
        viewModel.saveReadingProgress(bookId, currentChapterIndex, 0)
        listState.scrollToItem(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = book.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = textPrimaryColor
                        )
                        Text(
                            text = chapter.title,
                            fontSize = 11.sp,
                            color = textPrimaryColor.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("reader_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textPrimaryColor
                        )
                    }
                },
                actions = {
                    // Customize text font size & colors
                    IconButton(onClick = { showSettingsSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.TextFields,
                            contentDescription = "Configuración de texto",
                            tint = textPrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = containerBgColor,
                    titleContentColor = textPrimaryColor
                )
            )
        },
        bottomBar = {
            // Reader Navigation Bar (Previous Chapter / Next Chapter)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = containerBgColor,
                border = BorderStroke(1.dp, textPrimaryColor.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        enabled = currentChapterIndex > 0,
                        onClick = { currentChapterIndex-- },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = textPrimaryColor,
                            disabledContentColor = textPrimaryColor.copy(alpha = 0.25f)
                        ),
                        modifier = Modifier.testTag("prev_chap_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.ArrowBackIos, contentDescription = "Capítulo anterior", modifier = Modifier.size(12.dp))
                            Text("Ant.", fontSize = 13.sp)
                        }
                    }
                    
                    Text(
                        text = "Cap. ${currentChapterIndex + 1} de ${book.chapters.size}",
                        fontSize = 12.sp,
                        color = textPrimaryColor.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold
                    )
                    
                    TextButton(
                        enabled = currentChapterIndex < book.chapters.lastIndex,
                        onClick = { currentChapterIndex++ },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = textPrimaryColor,
                            disabledContentColor = textPrimaryColor.copy(alpha = 0.25f)
                        ),
                        modifier = Modifier.testTag("next_chap_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Sig.", fontSize = 13.sp)
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = "Siguiente capítulo", modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        },
        containerColor = containerBgColor,
        contentColor = textPrimaryColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp)
            ) {
                item {
                    // Header inside book
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = book.title.uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = textPrimaryColor.copy(alpha = 0.5f),
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = chapter.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp,
                            color = textPrimaryColor
                        )
                        Box(
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .size(width = 60.dp, height = 3.dp)
                                .background(textPrimaryColor.copy(alpha = 0.2f), RoundedCornerShape(2.dp))
                        )
                    }
                }
                
                // Content Paragraphs
                itemsIndexed(chapter.paragraphs) { paragraphIndex, paragraphText ->
                    Text(
                        text = paragraphText,
                        fontSize = (16 * settings.fontSizeMultiplier).sp,
                        fontFamily = FontFamily.Serif,
                        lineHeight = (25 * settings.fontSizeMultiplier).sp,
                        color = textPrimaryColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Save paragraph bookmark on click
                                viewModel.saveReadingProgress(bookId, currentChapterIndex, paragraphIndex)
                                coroutineScope.launch {
                                    // Visual confirmation toast/indicator could be added
                                }
                            }
                            .background(
                                color = if (savedProg.currentParagraphIndex == paragraphIndex) textPrimaryColor.copy(
                                    alpha = 0.08f
                                ) else Color.Transparent,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(8.dp)
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "— Fin del Capítulo —",
                        style = MaterialTheme.typography.bodySmall,
                        color = textPrimaryColor.copy(alpha = 0.4f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Quick Floating Indicator if paragraph is bookmarked
            if (savedProg.currentParagraphIndex > 0) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Punto de guardado",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Párrafo ${savedProg.currentParagraphIndex + 1} guardado",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
        
        // Text configuration Drawer Sheet
        if (showSettingsSheet) {
            AlertDialog(
                onDismissRequest = { showSettingsSheet = false },
                title = { Text("Ajustes de Lectura", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Section: Font Size
                        Text("Tamaño de Letra", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { viewModel.setFontSizeMultiplier(settings.fontSizeMultiplier - 0.15f) },
                                enabled = settings.fontSizeMultiplier > 0.75f,
                                modifier = Modifier.testTag("dec_font_btn")
                            ) {
                                Text("A-", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            Text(
                                "%.0f%%".format(settings.fontSizeMultiplier * 100),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            TextButton(
                                onClick = { viewModel.setFontSizeMultiplier(settings.fontSizeMultiplier + 0.15f) },
                                enabled = settings.fontSizeMultiplier < 1.8f,
                                modifier = Modifier.testTag("inc_font_btn")
                            ) {
                                Text("A+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Section: Color Schemes
                        Text("Paleta de Color", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val themes = listOf(
                                Triple(ReadingTheme.Cream, "Crema", Color(0xFFFDFBF7)),
                                Triple(ReadingTheme.Sepia, "Sepia", Color(0xFFF4ECD8)),
                                Triple(ReadingTheme.Sage, "Verde", Color(0xFFF1F5F0)),
                                Triple(ReadingTheme.Night, "Noche", Color(0xFF0F172A))
                            )
                            themes.forEach { (themeType, label, color) ->
                                Box(
                                    modifier = Modifier
                                        .size(width = 56.dp, height = 42.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(color)
                                        .border(
                                            width = if (settings.theme == themeType) 2.dp else 1.dp,
                                            color = if (settings.theme == themeType) MaterialTheme.colorScheme.primary else Color.LightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { viewModel.setReadingTheme(themeType) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (themeType == ReadingTheme.Night) Color.White else Color(0xFF433422)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showSettingsSheet = false },
                        modifier = Modifier.testTag("apply_reader_settings")
                    ) {
                        Text("Aplicar")
                    }
                }
            )
        }
    }
}

// === AUDIOBOOK PLAYER SCREEN (OFFLINE SPEECH SYNTHESIS ENGINE) ===

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudiobookPlayerScreen(
    bookId: String,
    viewModel: BookViewModel,
    onBack: () -> Unit
) {
    val book = viewModel.books.first { it.id == bookId }
    val audioState by viewModel.audioState.collectAsState()
    val progressMap by viewModel.progressMap.collectAsState()
    val currentLang by viewModel.language.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Automatically make sure this audiobook is selected in the viewModel when entering
    LaunchedEffect(bookId) {
        if (audioState.activeBook?.id != bookId) {
            viewModel.selectAudiobook(book)
        }
    }

    // Scroll to the speaking paragraph automatically
    LaunchedEffect(audioState.currentParagraphIdx, audioState.currentChapterIdx) {
        if (audioState.activeBook?.id == bookId) {
            try {
                listState.animateScrollToItem(audioState.currentParagraphIdx)
            } catch (e: Exception) {
                // Fail-safe
            }
        }
    }

    val activeChapter = book.chapters.getOrNull(audioState.currentChapterIdx) ?: book.chapters[0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Translator.translate("menu_audiobooks", currentLang),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("player_back_btn")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Translator.translate("back_btn", currentLang)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            // Retro spinning Disc Cover Container
            SpinningDiscAndCover(
                book = book,
                isPlaying = audioState.isPlaying
            )

            // Book Details
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                
                Text(
                    text = "por ${book.author}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Card(
                    modifier = Modifier.padding(top = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = activeChapter.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Real-time custom voice wave equalizer anim
            VoiceWaveformVisualizer(isPlaying = audioState.isPlaying)

            // Paragraph list read along viewer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(activeChapter.paragraphs) { idx, text ->
                        val isCurrent = idx == audioState.currentParagraphIdx
                        val bg = if (isCurrent) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else Color.Transparent
                        val textF = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                        val textC = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(bg)
                                .clickable {
                                    // Let the user tap to jump TTS directly to that paragraph! Very handy.
                                    if (audioState.isTtsReady) {
                                        viewModel.saveReadingProgress(book.id, audioState.currentChapterIdx, idx)
                                        viewModel.selectAudiobook(book)
                                        if (audioState.isPlaying) viewModel.playAudiobook()
                                    }
                                }
                                .padding(8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                if (isCurrent) {
                                    Icon(
                                        imageVector = Icons.Default.Hearing,
                                        contentDescription = "Escuchando",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(top = 2.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${idx + 1}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                                Text(
                                    text = text,
                                    fontSize = 13.sp,
                                    fontWeight = textF,
                                    color = textC,
                                    fontFamily = FontFamily.Serif,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // Warning Banner for Speech
            if (audioState.errorMsg != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = audioState.errorMsg ?: "Error",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Playback rate adjusting bar (0.5x, 1.0x, 1.5x, 2.0x)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = Translator.translate("playback_speed", currentLang),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val speeds = listOf(0.7f, 1.0f, 1.3f, 1.6f, 2.0f)
                    speeds.forEach { speedVal ->
                        val selected = audioState.speed == speedVal
                        FilterChip(
                            selected = selected,
                            onClick = { viewModel.setPlaybackSpeed(speedVal) },
                            label = { Text("${speedVal}x", fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("speed_chip_${speedVal}")
                        )
                    }
                }
            }

            // Main Media Player Controller Row (48dp target aligned)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back paragraph (48dp target)
                    IconButton(
                        onClick = { viewModel.skipBackward() },
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("btn_skip_prev")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Párrafo anterior",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Main play/pause trigger (56dp target)
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .clickable {
                                if (audioState.isPlaying) viewModel.pauseAudiobook() else viewModel.playAudiobook()
                            }
                            .testTag("btn_main_play"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Reproducir / Pausa",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Forward paragraph (48dp target)
                    IconButton(
                        onClick = { viewModel.skipForward() },
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("btn_skip_forward")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Siguiente párrafo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Extra chapter seek selection row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.seekAudiobookChapter(audioState.currentChapterIdx - 1) },
                    enabled = audioState.currentChapterIdx > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("Cap. anterior", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }

                Text(
                    text = "Capítulo ${audioState.currentChapterIdx + 1} de ${book.chapters.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = { viewModel.seekAudiobookChapter(audioState.currentChapterIdx + 1) },
                    enabled = audioState.currentChapterIdx < book.chapters.lastIndex,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Text("Cap. siguiente", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun SpinningDiscAndCover(
    book: Book,
    isPlaying: Boolean
) {
    // Dynamic Rotation Angle state
    val infiniteTransition = rememberInfiniteTransition(label = "rotation_transition")
    val rotationAngle by if (isPlaying) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "cover_rot"
        )
    } else {
        remember { mutableStateOf(0f) }
    }

    Box(
        modifier = Modifier
            .size(240.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Rear shadow vinyl outline disc
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = rotationAngle }
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2
            
            // Draw Vinyl Grooves
            drawCircle(
                color = Color(0xFF1E293B),
                radius = radius,
                center = center
            )
            // Lines representing grooves
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = radius * 0.9f,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5.dp.toPx())
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = radius * 0.75f,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = radius * 0.62f,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
            )
        }

        // Concentric elegant book cover inside the vinyl center
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color(book.coverBg))
                .border(2.dp, Color.White.copy(alpha = 0.8f), CircleShape)
                .graphicsLayer { rotationZ = rotationAngle },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = book.coverEmoji,
                    fontSize = 32.sp
                )
                Text(
                    text = book.title,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun VoiceWaveformVisualizer(isPlaying: Boolean) {
    val transition = rememberInfiniteTransition(label = "wave_anim")
    
    // Create height scaling for multiple bars to resemble a real vocal layout
    val barCount = 15
    val waveHeights = (0 until barCount).map { i ->
        if (isPlaying) {
            transition.animateFloat(
                initialValue = 0.15f,
                targetValue = 0.9f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 300 + (sin(i.toDouble()) * 180 + 100).toInt()
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "w_h_$i"
            )
        } else {
            remember { mutableStateOf(0.12f) }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "NARRADOR OFF-LINE",
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            waveHeights.forEach { heightState ->
                Box(
                    modifier = Modifier
                        .size(width = 4.dp, height = 30.dp)
                        .fillMaxHeight(heightState.value)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}
