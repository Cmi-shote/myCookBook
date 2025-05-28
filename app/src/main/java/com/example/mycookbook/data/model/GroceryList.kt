package com.example.mycookbook.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Grocery(
    @SerializedName("recipeDetails")
    val recipeDetails: RecipeDetails,
    @SerializedName("current")
    val current: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("date")
    val date: String
) : Parcelable