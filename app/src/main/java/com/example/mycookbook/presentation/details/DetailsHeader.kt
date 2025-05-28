package com.example.mycookbook.presentation.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.utils.CounterButton
import com.example.mycookbook.presentation.utils.getNutritionInfo

@Composable
fun DetailsHeader(
    modifier: Modifier = Modifier,
    selectedRecipe: RecipeDetails
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = selectedRecipe.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Breakfast / ${selectedRecipe.readyInMinutes} mins", //todo
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            val nutritionInfo = selectedRecipe.getNutritionInfo()

            Log.d("NutritionInfo", nutritionInfo.toString())
            // Extract numeric values for progress calculation
            val proteinValue = nutritionInfo.protein.replace("g of protein", "").toFloatOrNull() ?: 0f
            val fatValue = nutritionInfo.fat.replace("g of fat", "").toFloatOrNull() ?: 0f
            val caloriesValue = nutritionInfo.calories.replace(" calories", "").toFloatOrNull() ?: 0
            Log.d()

            NutritionInfoItem(
                label = "Protein",
                value = nutritionInfo.protein.ifEmpty { "0" } + "g",
                progress = ((proteinValue / 50f) * 100f).coerceAtMost(100f) // Assuming 50g daily goal
            )

            NutritionInfoItem(
                label = "Fat",
                value = nutritionInfo.fat.ifEmpty { "0" } + "g",
                progress = (fatValue / 65f * 100f).coerceAtMost(100f) // Assuming 65g daily goal
            )

            NutritionInfoItem(
                label = "Calories",
                value = nutritionInfo.calories.ifEmpty { "0" } + "k",
                progress = (caloriesValue / 2000f * 100f).coerceAtMost(100f) // Assuming 2000 cal daily goal
            )
        }

        Spacer(modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "4 serves",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            CounterButton()
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}