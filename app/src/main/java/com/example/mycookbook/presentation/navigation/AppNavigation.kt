package com.example.mycookbook.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.details.DetailsScreen
import com.example.mycookbook.presentation.landingPage.LandingPage
import com.example.mycookbook.presentation.recipes.RecipesScreen
import com.example.mycookbook.presentation.splashScreen.SplashScreen
import com.example.mycookbook.sampleRecipeDetails
import com.example.mycookbook.trending
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(navController: NavHostController, nextDestination: AppRoute) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashScreen
    ) {
        composable<AppRoute.SplashScreen> {
            SplashScreen(
                navigateToPage = {
                    navController.popBackStack()
                    navController.navigate(nextDestination)
                }
            )
        }

        // Onboarding screen
        composable<AppRoute.OnboardingRoute> {
            LandingPage(
                onButtonClick = {
                    navController.navigate(AppRoute.RecipeMainRoute)
                }
            )
        }

        // Main Screen
        composable<AppRoute.RecipeMainRoute> {
            RecipesScreen(
                recipeDetails = sampleRecipeDetails,
                trending = trending,
                onRecipeClick = { selectedRecipe ->
                    navController.navigate(AppRoute.RecipeDetailsRoute(selectedRecipe))
                }
            )
        }

        // Recipe Details Screen
        composable<AppRoute.RecipeDetailsRoute>(
            typeMap = mapOf(
                typeOf<RecipeDetails>() to CustomNavType(
                    RecipeDetails.serializer()
                )
            )
        ) { backStackEntry ->
           val args = backStackEntry.toRoute<AppRoute.RecipeDetailsRoute>()
            DetailsScreen(
                selectedRecipe = args.selectedRecipe,
            )
        }
    }
}