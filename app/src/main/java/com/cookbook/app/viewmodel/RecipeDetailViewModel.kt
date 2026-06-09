package com.cookbook.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookbook.app.data.repository.RecipeRepository
import com.cookbook.app.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true,
    val currentImageIndex: Int = 0,
    val showIngredients: Boolean = false
)

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Long = savedStateHandle.get<Long>("recipeId") ?: 0L

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getRecipeById(recipeId).collect { recipe ->
                _uiState.update { it.copy(recipe = recipe, isLoading = false) }
            }
        }
    }

    fun toggleFavorite() {
        val recipe = _uiState.value.recipe ?: return
        viewModelScope.launch {
            repository.toggleFavorite(recipe.id, !recipe.isFavorite)
        }
    }

    fun setImageIndex(index: Int) {
        _uiState.update { it.copy(currentImageIndex = index) }
    }

    fun toggleIngredientsPanel() {
        _uiState.update { it.copy(showIngredients = !it.showIngredients) }
    }
}
