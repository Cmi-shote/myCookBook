package com.example.mycookbook.data.model

data class RecipeDetails(
    val foodImage: Int,
    val title: String,
    val mealType: String,
    val time: String,
    val nutritionInfo: List<NutritionalDetails>,
    val ingredients: List<Ingredient>,
    val directions: String
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

data class Direction(
    val step: Int,
    val description: String,
    val totalSteps: Int
)

fun String.toDirectionList(): List<Direction> {
    val directions = this.split(".")
        .map { it.trim() }         // Trim spaces around each step
        .filter { it.isNotEmpty() } // Remove empty directions

    return directions.mapIndexed { index, description ->
        Direction(index + 1, description, directions.size)
    }
}