package com.example.mycookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.data.model.NutritionalDetails
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.details.DetailsScreen
import com.example.mycookbook.ui.theme.MyCookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCookBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailsScreen(
                        modifier = Modifier.padding(innerPadding),
                        selectedRecipe = RecipeDetails(
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
                            directions = "Cook pasta. Cook pasta. Cook pasta"
                        )
                    )
                }
            }
        }
    }
}

val sampleIngredient = (1..10).map {
    Ingredient(
        image = R.drawable.images,
        name = "Tomatoes",
        quantity = "500 g"
    )
}
