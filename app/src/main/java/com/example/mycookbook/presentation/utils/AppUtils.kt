package com.example.mycookbook.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.mycookbook.data.model.FoodRecipe
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.util.NetworkResult
import retrofit2.Response

fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
    when {
        response.message().toString().contains("timeout") -> {
            return NetworkResult.Error("Timeout")
        }
        response.code() == 402 -> {
            return NetworkResult.Error("API Key Limited.")
        }
        response.body()!!.results.isEmpty() -> {
            return NetworkResult.Error("Recipes not found.")
        }
        response.isSuccessful -> {
            val foodRecipes = response.body()
            return NetworkResult.Success(foodRecipes!!)
        }
        else -> {
            return NetworkResult.Error(response.message())
        }
    }
}

data class NutritionInfo(
    val protein: String = "",
    val fat: String = "",
    val calories: String = ""
)

fun extractNutritionFromSummary(summary: String): NutritionInfo {
    val proteinRegex = """<b>(\d+)g of protein</b>""".toRegex()
    val fatRegex = """<b>(\d+)g of fat</b>""".toRegex()
    val caloriesRegex = """<b>(\d+) calories</b>""".toRegex()

    val protein = proteinRegex.find(summary)?.groupValues?.get(1) ?: "0"
    val fat = fatRegex.find(summary)?.groupValues?.get(1) ?: "0"
    val calories = caloriesRegex.find(summary)?.groupValues?.get(1) ?: "0"

    return NutritionInfo(protein, fat, calories)
}

// You could also add this as an extension function to your RecipeDetails class
fun RecipeDetails.getNutritionInfo(): NutritionInfo {
    return extractNutritionFromSummary(this.summary)
}

