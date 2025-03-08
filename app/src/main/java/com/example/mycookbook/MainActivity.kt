package com.example.mycookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mycookbook.presentation.LandingPage
import com.example.mycookbook.ui.theme.MyCookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCookBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}