package com.example.mycookbook.data.database

import com.example.mycookbook.data.database.network.FoodRecipesApi
import com.example.mycookbook.data.model.FoodRecipe
import retrofit2.Response

class RemoteDataSource (
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

//    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
//        return foodRecipesApi.getFoodJoke(apiKey)
//    }

}