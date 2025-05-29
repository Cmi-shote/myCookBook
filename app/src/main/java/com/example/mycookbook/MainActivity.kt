package com.example.mycookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.mycookbook.presentation.navigation.AppNavigation
import com.example.mycookbook.presentation.navigation.AppRoute
import com.example.mycookbook.presentation.utils.UserPreferences
import com.example.mycookbook.ui.theme.MyCookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferences = UserPreferences(applicationContext)
        setContent {
            MyCookBookTheme {
                val navController = rememberNavController()
                val onboardingSeen by userPreferences.onboardingSeen.collectAsState(initial = false)

                AppNavigation(
                    navController = navController,
                    nextDestination = if (onboardingSeen) AppRoute.MainWithBottomNav else AppRoute.OnboardingRoute
                )
            }
        }
    }
}