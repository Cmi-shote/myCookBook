package com.example.mycookbook.presentation.details

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycookbook.data.database.entities.FavoritesEntity
import com.example.mycookbook.data.database.entities.GroceryEntity
import com.example.mycookbook.data.model.Grocery
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.recipes.RecipesViewModel
import com.example.mycookbook.presentation.utils.ButtonGroup
import com.example.mycookbook.presentation.utils.formatRecipeSummary
import java.time.LocalDate

@Composable
fun DetailsPage(
    modifier: Modifier = Modifier,
    selectedRecipe: RecipeDetails,
    viewModel: RecipesViewModel,
    onNavigateToSource: (String) -> Unit
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(selectedRecipe.recipeId) {
        isFavorite = viewModel.isFavorite(selectedRecipe.recipeId)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            DetailsHeader(selectedRecipe = selectedRecipe)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text(
                    text = "Summary", //todo: add to string file
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ListItem(
                headlineContent = {},
                supportingContent = {
                    Log.d("DetailsPage", "Summary: ${selectedRecipe.summary.formatRecipeSummary()}")
                    Text(text = selectedRecipe.summary.formatRecipeSummary())
                },
                modifier = modifier
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Replace your ButtonGroup section in DetailsPage with this:

            ButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                buttonLabelTwo = "Cook Dish",
                onFavoriteToggle = {
                    // Update UI state immediately
                    isFavorite = !isFavorite

                    // Then perform database operation in background
                    if (isFavorite) {
                        // Add to favorites
                        val favoritesEntity = FavoritesEntity(
                            id = 0,
                            result = selectedRecipe
                        )
                        viewModel.insertFavoriteRecipe(favoritesEntity)
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        // Remove from favorites
                        viewModel.deleteFavoriteRecipe(selectedRecipe.recipeId)
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    }
                },
                buttonActionTwo = {
                    onNavigateToSource(selectedRecipe.sourceUrl)
                },
                shouldTrailingIconTwoShow = true,
                isFavorite = isFavorite
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

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
                        text = selectedRecipe.servings.toString() + " serves",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(selectedRecipe.extendedIngredients) { ingredient ->
            Ingredients(ingredient = ingredient)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    // Add to grocery list
                    val groceryEntity = GroceryEntity(
                        id = 0,
                        result = Grocery(
                            recipeDetails = selectedRecipe,
                            current = 0,
                            total = selectedRecipe.extendedIngredients.size,
                            date = LocalDate.now().toString()
                        )
                    )
                    viewModel.insertGroceryItem(groceryEntity)
                    Toast.makeText(context, "Added to grocery list", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add to Shopping List",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
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


//@Preview(showBackground = true)
//@Composable
//fun DetailsPagePreview() {
//    MyCookBookTheme {
//        DetailsPage(
//            selectedRecipe = RecipeDetails(
//                foodImage = R.drawable.medium,
//                title = "Creamy Pasta",
//                mealType = "Dessert",
//                time = "30 mins",
//                nutritionInfo = listOf(
//                    NutritionalDetails("Energy", "285 k", 0.75f),
//                    NutritionalDetails("Protein", "23 g", 0.5f),
//                    NutritionalDetails("Carbs", "47 g", 0.6f),
//                    NutritionalDetails("Fat", "7 g", 0.3f)
//                ),
//                ingredients = sampleIngredient,
//                directions = "Cook pasta. Cook pasta. Cook pasta",
//                servings = "2"
//            )
//        )
//    }
//}