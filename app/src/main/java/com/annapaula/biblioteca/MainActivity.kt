package com.annapaula.biblioteca

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.annapaula.biblioteca.api.RetrofitClient
import com.annapaula.biblioteca.ui.BuscaScreen
import com.annapaula.biblioteca.ui.theme.BibliotecaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            BibliotecaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    BuscaScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}