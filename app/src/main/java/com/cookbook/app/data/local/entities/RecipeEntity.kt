package com.cookbook.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "recipes")
@TypeConverters(StringListConverter::class)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val cookingTimeMinutes: Int,
    val servings: Int,
    val description: String,
    val ingredients: List<String>,       // stored as JSON
    val instructions: List<String>,      // stored as JSON
    val imageUrls: List<String>,         // at least 15 images across all recipes
    val videoUrl: String? = null,        // at least 1 video
    val audioUrl: String? = null,        // at least 1 audio
    val isFavorite: Boolean = false,
    val rating: Float = 0f,
    val createdAt: Long = System.currentTimeMillis()
)

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}
