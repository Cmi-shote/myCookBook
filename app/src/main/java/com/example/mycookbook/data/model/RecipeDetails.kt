package com.example.mycookbook.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RecipeDetails(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredient>,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("id")
    val recipeId: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Float,
    @SerializedName("dishTypes")
    val dishTypes: List<String>,
    @SerializedName("servings")
    val servings: Int,

): Parcelable

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