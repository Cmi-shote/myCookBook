package com.example.mycookbook.presentation.navigation

import com.example.mycookbook.data.model.RecipeDetails
import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object OnboardingRoute : AppRoute

    @Serializable
    data object RecipeMainRoute : AppRoute

    @Serializable
    data object ExploreRoute : AppRoute

    @Serializable
    data object GroceryRoute : AppRoute

    @Serializable
    data class RecipeDetailsRoute(val selectedRecipe: RecipeDetails): AppRoute

    @Serializable
    data object SplashScreen : AppRoute
}