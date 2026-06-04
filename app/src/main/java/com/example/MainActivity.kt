package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.BookLibraryApp
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BookViewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {
  private val viewModel: BookViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val themeMode by viewModel.themeMode.collectAsState()
      val useDarkTheme = when (themeMode) {
        "CLARO" -> false
        "OSCURO" -> true
        else -> isSystemInDarkTheme()
      }
      MyApplicationTheme(darkTheme = useDarkTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
          BookLibraryApp(viewModel = viewModel)
        }
      }
    }
  }
}
