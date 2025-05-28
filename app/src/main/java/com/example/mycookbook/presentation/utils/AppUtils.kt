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

/**
 * Helper function to clean and format HTML strings from recipe summaries
 */
/**
 * Helper function to clean and format HTML strings from recipe summaries
 */
fun String.formatRecipeSummary(): String {
    return this
        // First, handle the specific malformed pattern from your example
        .replace(Regex("<%([^<>%]+)<%"), "$1") // Fix <%content<% pattern

        // Fix various malformed HTML tag patterns
        .replace(Regex("</?(b|strong|i|em)>?"), "") // Remove opening/closing formatting tags
        .replace("/b>", "") // Fix incomplete closing bold tags
        .replace("b>", "")  // Handle even more malformed tags

        // Handle corrupted characters that should be removed
        .replace("◊/b>", "")
        .replace("◊b>", "")
        .replace("◊>", "")
        .replace("◊", "")
        .replace("�", "") // Remove replacement characters

        // Remove HTML tags but keep the content
        .replace(Regex("<(b|strong|i|em)\\s*[^>]*>(.*?)</(b|strong|i|em)>"), "$2") // Remove formatting tags but keep content
        .replace(Regex("<a[^>]*>(.*?)</a>"), "$1") // Remove <a> tags but keep link text
        .replace(Regex("<[^>]+>"), "") // Remove any remaining HTML tags

        // Clean up common HTML entities
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&nbsp;", " ")
        .replace(Regex("&#\\d+;"), "") // Remove numeric HTML entities

        // Fix spacing around punctuation
        .replace(Regex("\\s+([,.!?])"), "$1") // Remove space before punctuation
        .replace(Regex("([,.!?])([A-Za-z])"), "$1 $2") // Add space after punctuation if missing

        // Clean up extra whitespace
        .replace(Regex("\\s+"), " ") // Replace multiple spaces with single space
        .replace(Regex("^\\s+|\\s+$"), "") // Remove leading/trailing whitespace more explicitly
}
