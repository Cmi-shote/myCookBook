package com.example.mycookbook.data.model

data class RecipeDetails(
    val foodImage: Int,
    val title: String,
    val mealType: String,
    val time: String,
    val nutritionInfo: List<NutritionalDetails>,
    val ingredients: List<Ingredient>
)

data class NutritionalDetails(
    val type: String,
    val value: String,
    val progress: Float
)

data class Ingredient(
    val image: Int,
    val name: String,
    val quantity: String
)