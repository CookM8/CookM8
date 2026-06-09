package com.cookbook.app.model

data class Recipe(
    val id: Long = 0,
    val title: String,
    val category: String,
    val cookingTimeMinutes: Int,
    val servings: Int,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrls: List<String>,
    val videoUrl: String? = null,
    val audioUrl: String? = null,
    val isFavorite: Boolean = false,
    val rating: Float = 0f,
    val createdAt: Long = System.currentTimeMillis()
)

data class Category(
    val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val recipeCount: Int = 0
)

// Navigation routes
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RecipeList : Screen("recipe_list/{category}") {
        fun createRoute(category: String) = "recipe_list/$category"
    }
    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(id: Long) = "recipe_detail/$id"
    }
    object Favorites : Screen("favorites")
    object AddRecipe : Screen("add_recipe")
    object Ingredients : Screen("ingredients/{recipeId}") {
        fun createRoute(id: Long) = "ingredients/$id"
    }
    object VideoPlayer : Screen("video_player/{recipeId}") {
        fun createRoute(id: Long) = "video_player/$id"
    }
}
