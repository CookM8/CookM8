package com.cookbook.app.ui.screens.recipedetail

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.cookbook.app.ui.components.*
import com.cookbook.app.ui.theme.GreenAccent
import com.cookbook.app.ui.theme.OrangeAccent
import com.cookbook.app.viewmodel.RecipeDetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RecipeDetailScreen(
    onBack: () -> Unit,
    onIngredientsClick: (Long) -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val recipe = state.recipe
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (state.isLoading || recipe == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = OrangeAccent)
        }
        return
    }

    val pagerState = rememberPagerState { recipe.imageUrls.size }

    var showVideo by remember { mutableStateOf(false) }
    val videoPlayer = remember(showVideo) {
        if (showVideo && recipe.videoUrl != null) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(recipe.videoUrl))
                prepare()
            }
        } else null
    }
    DisposableEffect(videoPlayer) { onDispose { videoPlayer?.release() } }

    var audioPlaying by remember { mutableStateOf(false) }
    val audioPlayer = remember {
        if (recipe.audioUrl != null) {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(recipe.audioUrl))
                prepare()
            }
        } else null
    }
    DisposableEffect(audioPlayer) { onDispose { audioPlayer?.release() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.title, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    FavoriteButton(isFavorite = recipe.isFavorite, onClick = viewModel::toggleFavorite)
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
        ) {
            // Image Gallery
            Box {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) { page ->
                    AsyncImage(
                        model = recipe.imageUrls[page],
                        contentDescription = "${recipe.title} image ${page + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                // Pager dots
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(recipe.imageUrls.size) { i ->
                        val selected = pagerState.currentPage == i
                        val width by animateDpAsState(
                            targetValue = if (selected) 20.dp else 8.dp,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "dot_width"
                        )
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(width)
                                .clip(CircleShape)
                                .background(if (selected) OrangeAccent else Color.White.copy(alpha = 0.6f))
                                .clickable { scope.launch { pagerState.animateScrollToPage(i) } }
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(recipe.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    InfoChip(icon = Icons.Filled.AccessTime, label = "${recipe.cookingTimeMinutes} min")
                    InfoChip(icon = Icons.Filled.People,     label = "${recipe.servings} servings")
                    InfoChip(icon = Icons.Filled.Category,   label = recipe.category)
                }

                Text(recipe.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Button(
                    onClick = { onIngredientsClick(recipe.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.List, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("View Ingredients")
                }

                // Video
                if (recipe.videoUrl != null) {
                    HorizontalDivider()
                    Text("Video Tutorial", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    if (!showVideo) {
                        OutlinedButton(
                            onClick = { showVideo = true },
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(2.dp, OrangeAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = OrangeAccent)
                            Spacer(Modifier.width(8.dp))
                            Text("Play Video", color = OrangeAccent)
                        }
                    } else {
                        videoPlayer?.let { player ->
                            AndroidView(
                                factory = { ctx -> PlayerView(ctx).apply { this.player = player } },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }

                // Audio
                if (recipe.audioUrl != null && audioPlayer != null) {
                    HorizontalDivider()
                    Text("Audio Guide", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    AudioPlayerBar(
                        isPlaying = audioPlaying,
                        onToggle = {
                            audioPlaying = !audioPlaying
                            if (audioPlaying) audioPlayer.play() else audioPlayer.pause()
                        }
                    )
                }

                // Instructions
                HorizontalDivider()
                Text("Instructions", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                recipe.instructions.forEachIndexed { index, step ->
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(index * 80L)
                        visible = true
                    }
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInHorizontally()
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StepBadge(number = index + 1)
                            Text(step, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = OrangeAccent)
            Text(label, fontSize = 12.sp)
        }
    }
}

@Composable
private fun AudioPlayerBar(isPlaying: Boolean, onToggle: () -> Unit) {
    val pulse by rememberInfiniteTransition(label = "audio_pulse").animateFloat(
        initialValue = 1f,
        targetValue = if (isPlaying) 1.08f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val btnSize = (48 * pulse).dp
            Box(
                modifier = Modifier
                    .size(btnSize)
                    .clip(CircleShape)
                    .background(GreenAccent)
                    .clickable { onToggle() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Column {
                Text("Audio cooking guide", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(
                    if (isPlaying) "Playing…" else "Tap to play",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}