package com.example.mycookbook.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mycookbook.data.model.RecipeDetails
import com.example.mycookbook.presentation.details.DetailsScreen
import com.example.mycookbook.presentation.explore.ExploreScreen
import com.example.mycookbook.presentation.grocery.GroceryScreen
import com.example.mycookbook.presentation.ingredients.IngredientsChecklistScreen
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
                    navController.navigate(AppRoute.MainWithBottomNav)
                }
            )
        }

        // Main Screen Container (handles all bottom nav screens internally)
        composable<AppRoute.MainWithBottomNav> {
            MainScreenWithBottomNav(navController = navController)
        }

        // Recipe Details Screen (separate from bottom nav)
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

        composable<AppRoute.GroceryCheckListRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoute.RecipeDetailsRoute>()
            IngredientsChecklistScreen(
                selectedRecipe = args.selectedRecipe
            )
        }
    }
}


@Composable
fun MainScreenWithBottomNav(navController: NavHostController) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val navigationItems = listOf(
        BottomNavItem("Recipes", Icons.Default.LocalDining, 0),
        BottomNavItem("Explore", Icons.Default.Search, 1),
        BottomNavItem("Grocery", Icons.AutoMirrored.Filled.PlaylistAddCheck, 2)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTabIndex == item.index,
                        onClick = {
                            selectedTabIndex = item.index
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
        // Content switching without navigation
        when (selectedTabIndex) {
            0 -> {
                RecipesScreen(
                    modifier = Modifier.padding(paddingValues),
                    recipeDetails = sampleRecipeDetails,
                    trending = trending,
                    onRecipeClick = { selectedRecipe ->
                        navController.navigate(AppRoute.RecipeDetailsRoute(selectedRecipe))
                    }
                )
            }
            1 -> {
                ExploreScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            2 -> {
                GroceryScreen(
                    modifier = Modifier.padding(paddingValues),
                    onItemClicked = { selectedRecipe ->
                        navController.navigate(AppRoute.GroceryCheckListRoute(selectedRecipe))
                    }
                )
            }
        }
    }
}

// Data class for bottom navigation items
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val index: Int
)