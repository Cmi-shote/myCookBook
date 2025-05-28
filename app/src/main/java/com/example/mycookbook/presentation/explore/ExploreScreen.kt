package com.example.mycookbook.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.recipes.RecipeCard
import com.example.mycookbook.presentation.recipes.RecipesViewModel
import com.example.mycookbook.util.NetworkResult

@Composable
fun ExploreScreen(modifier: Modifier = Modifier, viewModel: RecipesViewModel, onRecipeClick: (RecipeDetails) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val searchedRecipes by viewModel.searchedRecipes.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
            },
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (searchQuery.isNotEmpty()) {
            when (searchedRecipes) {
                is NetworkResult.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is NetworkResult.Success -> {
                    searchedRecipes.data?.results?.let { recipes ->
                        if (recipes.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(recipes) { recipe ->
                                    RecipeCard(data = recipe, onRecipeClick = onRecipeClick, modifier.fillMaxWidth())
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No recipes found")
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${(searchedRecipes as NetworkResult.Error).message}")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Start typing to search for recipes")
            }
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    viewModel: RecipesViewModel
) {
    OutlinedTextField(
        value = value,
        onValueChange = { query ->
            onValueChange(query)
            if (query.isNotEmpty()) {
                viewModel.searchRecipes(viewModel.applySearchQuery(query))
            }
        },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        placeholder = { Text("What are you craving?") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(32.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F6FA),
            focusedContainerColor = Color(0xFFF5F6FA),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}