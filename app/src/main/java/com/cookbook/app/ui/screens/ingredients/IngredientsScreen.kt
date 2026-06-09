package com.cookbook.app.ui.screens.ingredients

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cookbook.app.ui.components.IngredientRow
import com.cookbook.app.ui.theme.GreenAccent
import com.cookbook.app.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsScreen(
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val recipe = state.recipe
    val clipboard = LocalClipboardManager.current

    var checkedItems by remember { mutableStateOf(setOf<Int>()) }
    var showCopySnackbar by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(showCopySnackbar) {
        if (showCopySnackbar) {
            snackbarHostState.showSnackbar("Ingredients copied to clipboard!")
            showCopySnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ingredients", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenAccent)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            recipe?.let {
                Surface(shadowElevation = 8.dp) {
                    OutlinedButton(
                        onClick = {
                            val text = recipe.ingredients.joinToString("\n")
                            clipboard.setText(AnnotatedString(text))
                            showCopySnackbar = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, GreenAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = null, tint = GreenAccent)
                        Spacer(Modifier.width(8.dp))
                        Text("Copy Ingredient List", color = GreenAccent, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    ) { padding ->
        recipe?.let { r ->
            // Progress indicator
            val progress = if (r.ingredients.isEmpty()) 0f else checkedItems.size.toFloat() / r.ingredients.size
            Column(modifier = Modifier.padding(padding)) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = GreenAccent
                )
                Text(
                    "${checkedItems.size}/${r.ingredients.size} ingredients checked",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    itemsIndexed(r.ingredients) { index, ingredient ->
                        var visible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(index * 60L)
                            visible = true
                        }
                        AnimatedVisibility(visible = visible, enter = fadeIn() + slideInHorizontally()) {
                            IngredientRow(
                                text = ingredient,
                                checked = checkedItems.contains(index),
                                onToggle = {
                                    checkedItems = if (checkedItems.contains(index)) {
                                        checkedItems - index
                                    } else {
                                        checkedItems + index
                                    }
                                }
                            )
                        }
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}
