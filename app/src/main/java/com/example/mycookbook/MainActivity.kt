package com.example.mycookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.mycookbook.presentation.navigation.AppNavigation
import com.example.mycookbook.presentation.navigation.AppRoute
import com.example.mycookbook.presentation.utils.UserPreferences
import com.example.mycookbook.ui.theme.MyCookBookTheme
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferences = UserPreferences(applicationContext)
        setContent {
            MyCookBookTheme {
                val navController = rememberNavController()
                var nextDestination by remember { mutableStateOf<AppRoute?>(null) }

                LaunchedEffect(Unit) {
                    val onboardingSeen = userPreferences.onboardingSeen.first()
                    nextDestination = if (onboardingSeen) AppRoute.MainWithBottomNav else AppRoute.OnboardingRoute
                }

                nextDestination?.let { destination ->
                    AppNavigation(
                        navController = navController,
                        nextDestination = destination
                    )
                }
            }
        }
    }
}