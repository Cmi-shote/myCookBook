package com.example.mycookbook.presentation.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.components.ErrorScreen
import com.example.mycookbook.presentation.components.ShimmerLoadingScreen
import com.example.mycookbook.presentation.utils.hasInternetConnection
import com.example.mycookbook.util.NetworkResult
import java.util.Locale

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (RecipeDetails) -> Unit,
    viewModel: RecipesViewModel
) {
    val recipes by viewModel.recipes.collectAsState()
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    val popularRecipes by viewModel.popularRecipes.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (recipes) {
            is NetworkResult.Loading -> {
                if (hasInternetConnection(context)) {
                    ShimmerLoadingScreen()
                } else {
                    ErrorScreen(
                        message = "No Internet Connection",
                        onRetryClick = {
                            viewModel.getRecipes(viewModel.applyQueries())
                        }
                    )
                }
            }

            is NetworkResult.Success -> {
                recipes.data?.results.let { recipe ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            if (recipe != null) {
                                Text(
                                    text = "Recipes",
                                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                RecipeCardRow(
                                    recipeDetails = recipe,
                                    onRecipeClick = onRecipeClick,
                                    viewModel = viewModel
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            TrendingNowSection(
                                popularRecipes.results,
                                onRecipeClick = onRecipeClick
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            if (favoriteRecipes.isNotEmpty()) {
                                Text(
                                    text = "Favorite Recipes",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                RecipeCardRow(
                                    recipeDetails = favoriteRecipes.map { it.result },
                                    onRecipeClick = onRecipeClick,
                                    viewModel = viewModel
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }
                    }
                } ?: ErrorScreen(message = "No recipes found")
            }

            is NetworkResult.Error -> {
                ErrorScreen(
                    message = (recipes as NetworkResult.Error).message ?: "An error occurred",
                    onRetryClick = {
                        viewModel.getRecipes(viewModel.applyQueries())
                    }
                )
            }
        }
    }
}

@Composable
fun RecipeCardRow(
    recipeDetails: List<RecipeDetails>,
    onRecipeClick: (RecipeDetails) -> Unit,
    viewModel: RecipesViewModel
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipeDetails) { recipe ->
            RecipeCard(recipe, onRecipeClick, viewModel = viewModel)
        }
    }
}

@Composable
fun RecipeCard(
    data: RecipeDetails,
    onRecipeClick: (RecipeDetails) -> Unit,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(data.recipeId) {
        isFavorite = viewModel.isFavorite(data.recipeId)
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(250.dp)
            .height(250.dp)
            .clickable {
                onRecipeClick(data)
            },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = data.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Cook time",
                    tint = Color(0xFF7CA86E),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${data.readyInMinutes} mins",
                    fontSize = 12.sp,
                    color = Color(0xFF7CA86E)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Servings",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${data.servings} servings",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Dish type",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = data.dishTypes.take(2).joinToString(", ") {
                            it.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }
                        },
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite recipe",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TrendingNowSection(trending: List<RecipeDetails>, onRecipeClick: (RecipeDetails) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Trending now",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
//        TextButton(onClick = { /* TODO: View all trending */ }) {
//            Text(text = "View all", color = Color(0xFF7CA86E))
//        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(trending) { item ->
            TrendingCard(item, onRecipeClick = onRecipeClick)
        }
    }
}

@Composable
fun TrendingCard(data: RecipeDetails, onRecipeClick: (RecipeDetails) -> Unit) {
    Card(modifier = Modifier.clickable {
        onRecipeClick(data)
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(120.dp)
                .padding(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.image)
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(id = R.drawable.ic_placeholder), // Add your placeholder
//                error = painterResource(id = R.drawable.ic_error), // Add your error image
                contentDescription = data.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(90.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 3,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = data.dishTypes.take(2).joinToString(", ") {
                it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }, fontSize = 12.sp, color = Color.Gray, overflow = TextOverflow.Clip, maxLines = 1)
            Text(
                text = data.readyInMinutes.toString() + " mins",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}