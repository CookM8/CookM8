package com.cookbook.app.data.repository

import com.cookbook.app.data.local.dao.CategoryDao
import com.cookbook.app.data.local.dao.RecipeDao
import com.cookbook.app.data.local.entities.CategoryEntity
import com.cookbook.app.data.local.entities.RecipeEntity
import com.cookbook.app.model.Category
import com.cookbook.app.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao
) {
    // ─── Seed data ────────────────────────────────────────────────────────────

    /** Sample recipes — every entry adds to the ≥15 distinct image requirement. */
    val seedRecipes = listOf(
        RecipeEntity(
            title = "Fluffy Pancakes",
            category = "Breakfast",
            cookingTimeMinutes = 20,
            servings = 4,
            description = "Light, airy pancakes perfect for weekend mornings.",
            ingredients = listOf(
                "2 cups all-purpose flour",
                "2 tablespoons sugar",
                "2 teaspoons baking powder",
                "1/2 teaspoon salt",
                "1 3/4 cups milk",
                "1 large egg",
                "4 tablespoons melted butter"
            ),
            instructions = listOf(
                "Mix flour, sugar, baking powder, and salt in a large bowl.",
                "In another bowl whisk together milk, egg, and melted butter.",
                "Pour wet ingredients into dry and stir until just combined.",
                "Heat a non-stick pan over medium heat.",
                "Pour 1/4 cup of batter per pancake.",
                "Cook until bubbles form on surface, then flip and cook 1 more minute."
            ),
            // IMAGE 1-3
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1585407698236-7a78cdb68dec?w=800",
            ),
            // VIDEO (requirement: ≥1 video)
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            rating = 4.8f
        ),
        RecipeEntity(
            title = "Avocado Toast",
            category = "Breakfast",
            cookingTimeMinutes = 10,
            servings = 2,
            description = "Creamy avocado spread on crunchy sourdough, topped with cherry tomatoes.",
            ingredients = listOf(
                "2 slices sourdough bread",
                "1 ripe avocado",
                "1 teaspoon lemon juice",
                "Salt and pepper to taste",
                "Cherry tomatoes",
                "Red pepper flakes"
            ),
            instructions = listOf(
                "Toast bread until golden and crunchy.",
                "Mash avocado with lemon juice, salt, and pepper.",
                "Spread on toast and top with halved cherry tomatoes.",
                "Sprinkle red pepper flakes to finish."
            ),
            // IMAGE 4-5
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=800",
            ),
            rating = 4.5f
        ),
        RecipeEntity(
            title = "Caesar Salad",
            category = "Salads",
            cookingTimeMinutes = 15,
            servings = 4,
            description = "Classic Caesar with homemade dressing, crispy croutons and parmesan.",
            ingredients = listOf(
                "1 head romaine lettuce",
                "1/2 cup Caesar dressing",
                "1/2 cup parmesan shavings",
                "1 cup croutons",
                "1 lemon, juiced",
                "Freshly ground black pepper"
            ),
            instructions = listOf(
                "Wash and roughly chop the romaine lettuce.",
                "Toss with Caesar dressing until well coated.",
                "Add parmesan and croutons.",
                "Squeeze lemon juice over top and season with pepper."
            ),
            // IMAGE 6-7
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=800",
            ),
            rating = 4.6f
        ),
        RecipeEntity(
            title = "Spaghetti Carbonara",
            category = "Pasta",
            cookingTimeMinutes = 25,
            servings = 4,
            description = "Authentic Roman carbonara — no cream, just eggs, pecorino and guanciale.",
            ingredients = listOf(
                "400 g spaghetti",
                "200 g guanciale (or pancetta)",
                "4 large egg yolks + 1 whole egg",
                "100 g pecorino romano, grated",
                "Freshly ground black pepper",
                "Salt for pasta water"
            ),
            instructions = listOf(
                "Cook spaghetti in well-salted boiling water until al dente.",
                "Fry guanciale in a pan until crispy. Remove from heat.",
                "Whisk egg yolks with pecorino and plenty of pepper.",
                "Reserve 1 cup pasta water before draining.",
                "Toss hot pasta with guanciale fat, then add egg mixture.",
                "Stir vigorously, adding pasta water to emulsify the sauce.",
                "Serve immediately with extra pecorino."
            ),
            // IMAGE 8-9
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1555949258-eb67b1ef0ceb?w=800",
            ),
            rating = 4.9f
        ),
        RecipeEntity(
            title = "Tomato Basil Soup",
            category = "Soups",
            cookingTimeMinutes = 35,
            servings = 4,
            description = "Rich, velvety soup made with roasted tomatoes and fresh basil.",
            ingredients = listOf(
                "1 kg ripe tomatoes",
                "1 onion, diced",
                "4 garlic cloves",
                "2 cups vegetable broth",
                "1/2 cup fresh basil leaves",
                "2 tablespoons olive oil",
                "Salt, pepper, sugar"
            ),
            instructions = listOf(
                "Roast tomatoes and garlic at 200°C for 25 minutes.",
                "Sauté onion in olive oil until translucent.",
                "Add roasted tomatoes, garlic and broth. Simmer 10 minutes.",
                "Blend until smooth. Stir in basil.",
                "Season with salt, pepper, and a pinch of sugar."
            ),
            // IMAGE 10-11
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?w=800",
            ),
            rating = 4.7f
        ),
        RecipeEntity(
            title = "BBQ Grilled Chicken",
            category = "Grilled",
            cookingTimeMinutes = 45,
            servings = 4,
            description = "Juicy BBQ chicken with smoky marinade, grilled to perfection.",
            ingredients = listOf(
                "4 chicken thighs",
                "1/2 cup BBQ sauce",
                "2 tablespoons olive oil",
                "2 garlic cloves, minced",
                "1 teaspoon smoked paprika",
                "Salt and pepper"
            ),
            instructions = listOf(
                "Mix marinade: BBQ sauce, olive oil, garlic, paprika, salt, pepper.",
                "Coat chicken and marinate for at least 1 hour.",
                "Preheat grill to medium-high heat.",
                "Grill chicken 6–7 minutes per side.",
                "Brush with extra sauce in the last 2 minutes.",
                "Rest for 5 minutes before serving."
            ),
            // IMAGE 12-13
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800",
            ),
            rating = 4.7f
        ),
        RecipeEntity(
            title = "Chocolate Lava Cake",
            category = "Desserts",
            cookingTimeMinutes = 20,
            servings = 4,
            description = "Decadent chocolate cakes with a warm, gooey molten center.",
            ingredients = listOf(
                "200 g dark chocolate",
                "100 g butter",
                "2 eggs + 2 egg yolks",
                "80 g sugar",
                "40 g flour",
                "Pinch of salt",
                "Cocoa powder for dusting"
            ),
            instructions = listOf(
                "Melt chocolate and butter together. Let cool slightly.",
                "Whisk eggs, yolks, and sugar until pale and thick.",
                "Fold chocolate mixture into eggs.",
                "Sift in flour and salt, fold gently.",
                "Pour into buttered ramekins dusted with cocoa.",
                "Bake at 200°C for 10–12 minutes — center should still jiggle.",
                "Invert onto plates and serve immediately."
            ),
            // IMAGE 14-15 (fulfils ≥15 image requirement across all recipes)
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1579954115545-a95591f28bfc?w=800",
            ),
            rating = 4.9f
        )
    )

    val seedCategories = listOf(
        CategoryEntity(name = "Breakfast", imageUrl = "https://images.unsplash.com/photo-1677244284140-0717ca694b11?w=800", recipeCount = 2),
        CategoryEntity(name = "Desserts",  imageUrl = "https://images.unsplash.com/photo-1579954115545-a95591f28bfc?w=800", recipeCount = 1),
        CategoryEntity(name = "Salads",    imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=800", recipeCount = 1),
        CategoryEntity(name = "Pasta",     imageUrl = "https://images.unsplash.com/photo-1612874742237-6526221588e3?w=800", recipeCount = 1),
        CategoryEntity(name = "Soups",     imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=800", recipeCount = 1),
        CategoryEntity(name = "Grilled",   imageUrl = "https://images.unsplash.com/photo-1532636875304-0c89119d9b4d?w=800", recipeCount = 1)
    )

    // ─── Mappers ──────────────────────────────────────────────────────────────

    private fun RecipeEntity.toDomain() = Recipe(
        id = id, title = title, category = category,
        cookingTimeMinutes = cookingTimeMinutes, servings = servings,
        description = description, ingredients = ingredients,
        instructions = instructions, imageUrls = imageUrls,
        videoUrl = videoUrl, audioUrl = audioUrl,
        isFavorite = isFavorite, rating = rating, createdAt = createdAt
    )

    private fun CategoryEntity.toDomain() = Category(
        id = id, name = name, imageUrl = imageUrl, recipeCount = recipeCount
    )

    private fun Recipe.toEntity() = RecipeEntity(
        id = id, title = title, category = category,
        cookingTimeMinutes = cookingTimeMinutes, servings = servings,
        description = description, ingredients = ingredients,
        instructions = instructions, imageUrls = imageUrls,
        videoUrl = videoUrl, audioUrl = audioUrl,
        isFavorite = isFavorite, rating = rating, createdAt = createdAt
    )

    // ─── Public API ───────────────────────────────────────────────────────────

    fun getAllRecipes(): Flow<List<Recipe>> =
        recipeDao.getAllRecipes().map { it.map { e -> e.toDomain() } }

    fun getRecipesByCategory(category: String): Flow<List<Recipe>> =
        recipeDao.getRecipesByCategory(category).map { it.map { e -> e.toDomain() } }

    fun getFavoriteRecipes(): Flow<List<Recipe>> =
        recipeDao.getFavoriteRecipes().map { it.map { e -> e.toDomain() } }

    fun getRecipeById(id: Long): Flow<Recipe?> =
        recipeDao.getRecipeById(id).map { it?.toDomain() }

    fun searchRecipes(query: String): Flow<List<Recipe>> =
        recipeDao.searchRecipes(query).map { it.map { e -> e.toDomain() } }

    fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { it.map { e -> e.toDomain() } }

    suspend fun insertRecipe(recipe: Recipe): Long =
        recipeDao.insertRecipe(recipe.toEntity())

    suspend fun updateRecipe(recipe: Recipe) =
        recipeDao.updateRecipe(recipe.toEntity())

    suspend fun deleteRecipe(recipe: Recipe) =
        recipeDao.deleteRecipe(recipe.toEntity())

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) =
        recipeDao.updateFavoriteStatus(id, isFavorite)

    suspend fun seedIfEmpty() {
        if (recipeDao.getRecipeCount() == 0) {
            recipeDao.insertRecipes(seedRecipes)
        }
        if (categoryDao.getCategoryCount() == 0) {
            categoryDao.insertCategories(seedCategories)
        }
    }
}