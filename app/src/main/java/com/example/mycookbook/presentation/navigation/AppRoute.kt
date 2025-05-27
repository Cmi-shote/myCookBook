package com.example.mycookbook.presentation.navigation

import com.example.mycookbook.data.model.RecipeDetails
import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object OnboardingRoute : AppRoute

    @Serializable
    data class RecipeDetailsRoute(val selectedRecipe: RecipeDetails): AppRoute

    @Serializable
    data object SplashScreen : AppRoute

    @Serializable
    data object MainWithBottomNav : AppRoute

    @Serializable
    data class GroceryCheckListRoute(val selectedRecipe: RecipeDetails) : AppRoute
}