package com.example.mycookbook.data.database

import androidx.room.TypeConverter
import com.example.mycookbook.data.model.FoodRecipe
import com.example.mycookbook.data.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromFoodRecipe(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun toFoodRecipe(data: String): FoodRecipe {
        val type = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromResult(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun toResult(data: String): Result {
        val type = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, type)
    }
}
