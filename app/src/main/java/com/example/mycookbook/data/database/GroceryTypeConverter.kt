package com.example.mycookbook.data.database

import androidx.room.TypeConverter
import com.example.mycookbook.data.model.Grocery
import com.example.mycookbook.data.model.RecipeDetails
import com.google.gson.Gson

class GroceryTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromGrocery(grocery: Grocery): String {
        return gson.toJson(grocery)
    }

    @TypeConverter
    fun toGrocery(groceryString: String): Grocery {
        return gson.fromJson(groceryString, Grocery::class.java)
    }

    @TypeConverter
    fun fromRecipeDetails(recipeDetails: RecipeDetails): String {
        return gson.toJson(recipeDetails)
    }

    @TypeConverter
    fun toRecipeDetails(recipeDetailsString: String): RecipeDetails {
        return gson.fromJson(recipeDetailsString, RecipeDetails::class.java)
    }
}