package com.example.mycookbook.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mycookbook.data.database.Repository
import com.example.mycookbook.data.model.RecipeDetails

class AppViewModel(
    private val repository: Repository
): ViewModel() {
//    private val _selectedRecipe = mutableStateOf(RecipeDetails())
//    val selectedRecipe: State<RecipeDetails> = _selectedRecipe
//
//    fun updateRecipe(recipe: RecipeDetails) {
//        _selectedRecipe.value = recipe
//    }
}