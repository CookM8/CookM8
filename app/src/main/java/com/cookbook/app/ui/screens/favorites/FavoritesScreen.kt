package com.cookbook.app.ui.screens.favorites

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cookbook.app.ui.components.RecipeCard
import com.cookbook.app.ui.theme.OrangeAccent
import com.cookbook.app.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onRecipeClick: (Long) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Favorites", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OrangeAccent)
            )
        }
    ) { padding ->
        AnimatedContent(
            targetState = state.favorites.isEmpty() && !state.isLoading,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "favorites_state"
        ) { isEmpty ->
            if (isEmpty) {
                EmptyFavoritesState(
                    modifier = Modifier.fillMaxSize().padding(padding)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(padding)
                ) {
                    items(state.favorites, key = { it.id }) { recipe ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandVertically()
                        ) {
                            RecipeCard(
                                recipe = recipe,
                                onClick = { onRecipeClick(recipe.id) },
                                onFavoriteClick = { viewModel.removeFromFavorites(recipe) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoritesState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated scale on entry
        var animTrigger by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (animTrigger) 1.0f else 0.5f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            label = "heart_scale"
        )
        LaunchedEffect(Unit) { animTrigger = true }

        Icon(
            imageVector = Icons.Filled.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size((80 * scale).dp),
            tint = Color.LightGray
        )
        Spacer(Modifier.height(16.dp))
        Text("No Favorites Yet", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            "Browse recipes and tap ♥ to save your favorites here.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
