package com.example.mycookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.data.model.NutritionalDetails
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.data.model.Trending
import com.example.mycookbook.presentation.details.DetailsScreen
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
                    nextDestination = if (onboardingSeen) AppRoute.RecipeMainRoute else AppRoute.OnboardingRoute
                )
            }
        }
    }
}

val sampleIngredient = (1..5).map {
    Ingredient(
        image = R.drawable.images,
        name = "Tomatoes",
        quantity = "500 g"
    )
}

val sampleRecipeDetails = arrayListOf(
    RecipeDetails(
        foodImage = R.drawable.medium,
        title = "Creamy Pasta",
        mealType = "Dessert",
        time = "30 mins",
        nutritionInfo = listOf(
            NutritionalDetails("Energy", "285 k", 0.75f),
            NutritionalDetails("Protein", "23 g", 0.5f),
            NutritionalDetails("Carbs", "47 g", 0.6f),
            NutritionalDetails("Fat", "7 g", 0.3f)
        ),
        ingredients = sampleIngredient,
        directions = "Cook pasta. Cook pasta. Cook pasta",
        servings = "2"
    ),
    RecipeDetails(
        foodImage = R.drawable.salad,
        title = "Chicken Salad",
        mealType = "Appetizer",
        time = "10 mins",
        nutritionInfo = listOf(
            NutritionalDetails("Energy", "250 k", 0.75f),
            NutritionalDetails("Protein", "70 g", 0.5f),
            NutritionalDetails("Carbs", "97 g", 0.6f),
            NutritionalDetails("Fat", "17 g", 0.3f)
        ),
        ingredients = sampleIngredient,
        directions = "Dice chicken. Cook chicken. Cook chicken",
        servings = "2"
    )
)

val trending = listOf(
    Trending(R.drawable.salad, "Crem soup", "Lunch", "15 mins"),
    Trending(R.drawable.medium, "Creamy Pasta", "Dessert", "30 mins")
)