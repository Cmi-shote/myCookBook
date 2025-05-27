package com.example.mycookbook.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.mycookbook.data.model.Ingredient
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.details.DetailsScreen
import com.example.mycookbook.presentation.explore.ExploreScreen
import com.example.mycookbook.presentation.grocery.GroceryScreen
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
            MainScreenWithBottomNav(navController = navController)
//            RecipesScreen(
//                recipeDetails = sampleRecipeDetails,
//                trending = trending,
//                onRecipeClick = { selectedRecipe ->
//                    navController.navigate(AppRoute.RecipeDetailsRoute(selectedRecipe))
//                },
//            )
        }

        composable<AppRoute.ExploreRoute> {
            MainScreenWithBottomNav(navController = navController)
        }

        // Grocery Screen with Bottom Navigation
        composable<AppRoute.GroceryRoute> {
            MainScreenWithBottomNav(navController = navController)
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

@Composable
fun MainScreenWithBottomNav(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val navigationItems = listOf(
        BottomNavItem("Recipes", Icons.Default.LocalDining, AppRoute.RecipeMainRoute),
        BottomNavItem("Explore", Icons.Default.Search, AppRoute.ExploreRoute),
        BottomNavItem("Grocery", Icons.AutoMirrored.Filled.PlaylistAddCheck, AppRoute.GroceryRoute)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { item ->
                    val isSelected = currentRoute == item.route::class.qualifiedName

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination to avoid building up a large stack
                                    popUpTo(AppRoute.RecipeMainRoute) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title)
                        },
                        label = {
                            Text(
                                item.title,
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        // Content based on current route
        when (currentRoute) {
            AppRoute.RecipeMainRoute::class.qualifiedName -> {
                RecipesScreen(
                    modifier = Modifier.padding(paddingValues),
                    recipeDetails = sampleRecipeDetails,
                    trending = trending,
                    onRecipeClick = { selectedRecipe ->
                        navController.navigate(AppRoute.RecipeDetailsRoute(selectedRecipe))
                    }
                )
            }
            AppRoute.ExploreRoute::class.qualifiedName -> {
                ExploreScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            AppRoute.GroceryRoute::class.qualifiedName -> {
                GroceryScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

// Data class for bottom navigation items
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: AppRoute
)