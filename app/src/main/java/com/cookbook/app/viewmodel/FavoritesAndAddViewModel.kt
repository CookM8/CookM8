package com.cookbook.app.viewmodel

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookbook.app.data.repository.RecipeRepository
import com.cookbook.app.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

// ─── Favorites ────────────────────────────────────────────────────────────────

data class FavoritesUiState(
    val favorites: List<Recipe> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteRecipes().collect { favorites ->
                _uiState.update { it.copy(favorites = favorites, isLoading = false) }
            }
        }
    }

    fun removeFromFavorites(recipe: Recipe) {
        viewModelScope.launch { repository.toggleFavorite(recipe.id, false) }
    }
}

// ─── Add Recipe ───────────────────────────────────────────────────────────────

data class AddRecipeUiState(
    val title: String = "",
    val category: String = "Breakfast",
    val cookingTime: String = "",
    val servings: String = "",
    val description: String = "",
    val ingredientsText: String = "",
    val instructionsText: String = "",
    val imageUris: List<String> = emptyList(),
    val videoUri: String? = null,
    val isSaving: Boolean = false,
    val savedSuccessfully: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRecipeUiState())
    val uiState: StateFlow<AddRecipeUiState> = _uiState.asStateFlow()

    fun onTitleChange(v: String)       = _uiState.update { it.copy(title = v) }
    fun onCategoryChange(v: String)    = _uiState.update { it.copy(category = v) }
    fun onCookingTimeChange(v: String) = _uiState.update { it.copy(cookingTime = v) }
    fun onServingsChange(v: String)    = _uiState.update { it.copy(servings = v) }
    fun onDescriptionChange(v: String) = _uiState.update { it.copy(description = v) }
    fun onIngredientsChange(v: String) = _uiState.update { it.copy(ingredientsText = v) }
    fun onInstructionsChange(v: String)= _uiState.update { it.copy(instructionsText = v) }

    // ─── Media z galerii ──────────────────────────────────────────────────────

    /** Kopiuje wybrane zdjęcia do pamięci aplikacji i zapisuje ich trwałe ścieżki. */
    fun onImagesPicked(uris: List<Uri>) {
        if (uris.isEmpty()) return
        viewModelScope.launch {
            val paths = withContext(Dispatchers.IO) {
                uris.mapNotNull { copyToInternalStorage(it, "img") }
            }
            _uiState.update { it.copy(imageUris = it.imageUris + paths) }
        }
    }

    /** Kopiuje wybrany film do pamięci aplikacji i zapisuje jego trwałą ścieżkę. */
    fun onVideoPicked(uri: Uri?) {
        if (uri == null) return
        viewModelScope.launch {
            val path = withContext(Dispatchers.IO) { copyToInternalStorage(uri, "vid") }
            _uiState.update { it.copy(videoUri = path) }
        }
    }

    fun removeImage(path: String) =
        _uiState.update { it.copy(imageUris = it.imageUris - path) }

    fun clearVideo() = _uiState.update { it.copy(videoUri = null) }

    /**
     * URI z galerii (content://) dają tylko tymczasowy dostęp do pliku, więc kopiujemy
     * go do prywatnego katalogu aplikacji i zwracamy trwały adres file://, który zadziała
     * również po ponownym uruchomieniu aplikacji.
     */
    private fun copyToInternalStorage(uri: Uri, prefix: String): String? = try {
        val ext = context.contentResolver.getType(uri)
            ?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
            ?: "dat"
        val file = File(context.filesDir, "${prefix}_${System.currentTimeMillis()}.$ext")
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }
        Uri.fromFile(file).toString()
    } catch (e: Exception) {
        null
    }

    fun saveRecipe() {
        val s = _uiState.value
        if (s.title.isBlank()) {
            _uiState.update { it.copy(error = "Title cannot be empty") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val recipe = Recipe(
                title = s.title.trim(),
                category = s.category,
                cookingTimeMinutes = s.cookingTime.toIntOrNull() ?: 30,
                servings = s.servings.toIntOrNull() ?: 2,
                description = s.description.trim(),
                ingredients = s.ingredientsText.lines().filter { it.isNotBlank() },
                instructions = s.instructionsText.lines().filter { it.isNotBlank() },
                imageUrls = s.imageUris.ifEmpty {
                    listOf("https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=800")
                },
                videoUrl = s.videoUri
            )
            repository.insertRecipe(recipe)
            _uiState.update { it.copy(isSaving = false, savedSuccessfully = true) }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}