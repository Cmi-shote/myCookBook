package com.example.mycookbook.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetails(
    val foodImage: Int,
    val title: String,
    val mealType: String,
    val time: String,
    val servings: String,
    val nutritionInfo: List<NutritionalDetails>,
    val ingredients: List<Ingredient>,
    val directions: String
)

@Serializable
data class NutritionalDetails(
    val type: String,
    val value: String,
    val progress: Float
)

@Serializable
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

data class Trending(
    val imageRes: Int,
    val title: String,
    val type: String,
    val time: String
)

fun String.toDirectionList(): List<Direction> {
    val directions = this.split(".")
        .map { it.trim() }         // Trim spaces around each step
        .filter { it.isNotEmpty() } // Remove empty directions

    return directions.mapIndexed { index, description ->
        Direction(index + 1, description, directions.size)
    }
}