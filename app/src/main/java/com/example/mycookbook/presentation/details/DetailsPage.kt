package com.example.mycookbook.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycookbook.R
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.data.model.NutritionalDetails
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.utils.CounterButton
import com.example.mycookbook.presentation.utils.IngredientsList
import com.example.mycookbook.ui.theme.MyCookBookTheme

@Composable
fun DetailsPage(
    modifier: Modifier = Modifier,
    selectedRecipe: RecipeDetails
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = selectedRecipe.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${selectedRecipe.mealType} / ${selectedRecipe.time}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            selectedRecipe.nutritionInfo.forEach { (label, value, progress) ->
                NutritionInfoItem(label, value, progress)
            }
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

        IngredientsList(
            modifier = Modifier.fillMaxWidth(),
            ingredients = selectedRecipe.ingredients
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* Your action here */ },
//            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary) // Customize border color if needed
        ) {
            Text(text = "Add to Shopping List")
        }

    }
}


@Composable
fun NutritionInfoItem(label: String, value: String, progress: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(80.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(70.dp),
            color = Color(0xFF7CA86E), // Green color
            strokeWidth = 2.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsPagePreview() {
    MyCookBookTheme {
        val sampleIngredient = (1..10).map {
            Ingredient(
                image = R.drawable.images,
                name = "Tomatoes",
                quantity = "500 g"
            )
        }

        DetailsPage(
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
                ingredients = sampleIngredient
            )
        )
    }
}