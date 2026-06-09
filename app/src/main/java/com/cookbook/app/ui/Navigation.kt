package com.cookbook.app.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.cookbook.app.model.Screen
import com.cookbook.app.ui.screens.addrecipe.AddRecipeScreen
import com.cookbook.app.ui.screens.favorites.FavoritesScreen
import com.cookbook.app.ui.screens.home.HomeScreen
import com.cookbook.app.ui.screens.ingredients.IngredientsScreen
import com.cookbook.app.ui.screens.recipedetail.RecipeDetailScreen
import com.cookbook.app.ui.screens.recipelist.RecipeListScreen
import com.cookbook.app.ui.theme.OrangeAccent

private data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookbookNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("Home",      Icons.Filled.Home,         Screen.Home.route),
        BottomNavItem("Favorites", Icons.Filled.Favorite,     Screen.Favorites.route),
        BottomNavItem("Add",       Icons.Filled.AddCircle,    Screen.AddRecipe.route)
    )

    val showBottomBar = currentRoute in listOf(Screen.Home.route, Screen.Favorites.route, Screen.AddRecipe.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = Color.White) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = OrangeAccent,
                                selectedTextColor = OrangeAccent,
                                indicatorColor = Color(0xFFFFE0B2)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            exitTransition  = { slideOutHorizontally { -it } + fadeOut() },
            popEnterTransition  = { slideInHorizontally { -it } + fadeIn() },
            popExitTransition   = { slideOutHorizontally { it } + fadeOut() }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onCategoryClick = { cat -> navController.navigate(Screen.RecipeList.createRoute(cat)) },
                    onRecipeClick   = { id  -> navController.navigate(Screen.RecipeDetail.createRoute(id)) }
                )
            }
            composable(
                route = Screen.RecipeList.route,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) {
                RecipeListScreen(
                    onBack = { navController.popBackStack() },
                    onRecipeClick = { id -> navController.navigate(Screen.RecipeDetail.createRoute(id)) }
                )
            }
            composable(
                route = Screen.RecipeDetail.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
            ) {
                RecipeDetailScreen(
                    onBack = { navController.popBackStack() },
                    onIngredientsClick = { id -> navController.navigate(Screen.Ingredients.createRoute(id)) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onRecipeClick = { id -> navController.navigate(Screen.RecipeDetail.createRoute(id)) }
                )
            }
            composable(Screen.AddRecipe.route) {
                AddRecipeScreen(onBack = { navController.popBackStack() })
            }
            composable(
                route = Screen.Ingredients.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
            ) {
                IngredientsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
