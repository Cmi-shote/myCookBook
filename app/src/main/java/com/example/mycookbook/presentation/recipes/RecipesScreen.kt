package com.example.mycookbook.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycookbook.R
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.data.model.Trending

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    recipeDetails: List<RecipeDetails>,
    trending: List<Trending>,
    onRecipeClick: (RecipeDetails) -> Unit
) {
    Scaffold(
        topBar = { RecipesTopBar() },
        bottomBar = { RecipesBottomBar() }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Recipes",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(8.dp))
            RecipeCardRow(recipeDetails = recipeDetails, onRecipeClick = onRecipeClick)
            Spacer(modifier = Modifier.height(24.dp))
            TrendingNowSection(trending)
        }
    }
}

@Composable
fun RecipesTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        IconButton(onClick = { /* TODO: Add new recipe */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF7CA86E))
        }
    }
}

@Composable
fun RecipeCardRow(recipeDetails: List<RecipeDetails>, onRecipeClick: (RecipeDetails) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipeDetails) { recipe ->
            RecipeCard(recipe, onRecipeClick)
        }
    }
}

@Composable
fun RecipeCard(data: RecipeDetails, onRecipeClick: (RecipeDetails) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
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
            Image(
                painter = painterResource(id = data.foodImage),
                contentDescription = data.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = data.time, fontSize = 12.sp, color = Color(0xFF7CA86E))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = data.servings + "servings", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = data.mealType,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun TrendingNowSection(trending: List<Trending>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Trending now",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = { /* TODO: View all trending */ }) {
            Text(text = "View all", color = Color(0xFF7CA86E))
        }
    }
    TrendingNowRow(trending)
}

@Composable
fun TrendingNowRow(data: List<Trending>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data) { item ->
            TrendingCard(item)
        }
    }
}

@Composable
fun TrendingCard(data: Trending) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = data.title,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = data.type, fontSize = 12.sp, color = Color.Gray)
        Text(text = data.time, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun RecipesBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { /* TODO: Recipes */ },
            icon = { Icon(imageVector = Icons.Default.LocalDining, contentDescription = "Recipes") },
            label = { Text("Recipes") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Explore */ },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Explore") },
            label = { Text("Explore") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Grocery */ },
            icon = { Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistAddCheck, contentDescription = "Grocery") },
            label = { Text("Grocery") }
        )
    }
} 