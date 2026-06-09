package com.cookbook.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cookbook.app.data.local.dao.CategoryDao
import com.cookbook.app.data.local.dao.RecipeDao
import com.cookbook.app.data.local.entities.CategoryEntity
import com.cookbook.app.data.local.entities.RecipeEntity
import com.cookbook.app.data.local.entities.StringListConverter

@Database(
    entities = [RecipeEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class CookbookDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun categoryDao(): CategoryDao
}
