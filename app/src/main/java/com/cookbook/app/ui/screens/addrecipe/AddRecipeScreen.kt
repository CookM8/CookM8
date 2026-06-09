package com.cookbook.app.ui.screens.addrecipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cookbook.app.ui.theme.OrangeAccent
import com.cookbook.app.viewmodel.AddRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onBack: () -> Unit,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Launchery systemowego Photo Pickera (nie wymaga uprawnień runtime)
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris -> viewModel.onImagesPicked(uris) }

    val videoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> viewModel.onVideoPicked(uri) }

    // Navigate back after successful save
    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onBack()
    }

    val categories = listOf("Breakfast", "Desserts", "Salads", "Pasta", "Soups", "Grilled")
    var categoryExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Recipe", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OrangeAccent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Recipe Title *") },
                leadingIcon = { Icon(Icons.Filled.Title, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = state.category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = { viewModel.onCategoryChange(cat); categoryExpanded = false }
                        )
                    }
                }
            }

            // Time & Servings row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = state.cookingTime,
                    onValueChange = viewModel::onCookingTimeChange,
                    label = { Text("Time (min)") },
                    leadingIcon = { Icon(Icons.Filled.AccessTime, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
                )
                OutlinedTextField(
                    value = state.servings,
                    onValueChange = viewModel::onServingsChange,
                    label = { Text("Servings") },
                    leadingIcon = { Icon(Icons.Filled.People, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
                )
            }

            // Description
            OutlinedTextField(
                value = state.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2,
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
            )

            // Ingredients
            OutlinedTextField(
                value = state.ingredientsText,
                onValueChange = viewModel::onIngredientsChange,
                label = { Text("Ingredients (one per line)") },
                placeholder = { Text("2 cups flour\n1 cup sugar\n…") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 4,
                maxLines = 10,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
            )

            // Instructions
            OutlinedTextField(
                value = state.instructionsText,
                onValueChange = viewModel::onInstructionsChange,
                label = { Text("Instructions (one per line)") },
                placeholder = { Text("Preheat oven to 180°C\nMix dry ingredients\n…") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 5,
                maxLines = 12,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = OrangeAccent)
            )

            // ─── Zdjęcia z galerii ───
            // Miniatury wybranych zdjęć (z możliwością usunięcia)
            if (state.imageUris.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.imageUris.forEach { path ->
                        Box(modifier = Modifier.size(90.dp)) {
                            AsyncImage(
                                model = path,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            IconButton(
                                onClick = { viewModel.removeImage(path) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(26.dp)
                            ) {
                                Icon(Icons.Filled.Cancel, contentDescription = "Remove photo", tint = Color.White)
                            }
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, OrangeAccent)
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = null, tint = OrangeAccent)
                Spacer(Modifier.width(8.dp))
                Text("ADD PHOTO", color = OrangeAccent, fontWeight = FontWeight.SemiBold)
            }

            // ─── Film z galerii ───
            if (state.videoUri != null) {
                Card(shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Movie, contentDescription = null, tint = OrangeAccent)
                        Spacer(Modifier.width(8.dp))
                        Text("Video selected", modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.clearVideo() }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Remove video")
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = {
                        videoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, OrangeAccent)
                ) {
                    Icon(Icons.Filled.VideoLibrary, contentDescription = null, tint = OrangeAccent)
                    Spacer(Modifier.width(8.dp))
                    Text("ADD VIDEO", color = OrangeAccent, fontWeight = FontWeight.SemiBold)
                }
            }

            // Error
            AnimatedVisibility(visible = state.error != null) {
                state.error?.let { err ->
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                        Text(err, modifier = Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }

            // Save button
            Button(
                onClick = viewModel::saveRecipe,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                enabled = !state.isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = OrangeAccent),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Filled.Save, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Save Recipe", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}