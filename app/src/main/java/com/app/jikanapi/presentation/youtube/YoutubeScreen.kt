package com.app.jikanapi.presentation.youtube

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.app.jikanapi.data.utils.Utils.isInternetConnected
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeScreen(
    navController: NavHostController,
    youtubeId: String,
) {


    val lifecycleOwner = LocalLifecycleOwner.current
    var playbackPosition by rememberSaveable { mutableFloatStateOf(0f) }
    val context = LocalContext.current
    val orientation = remember { mutableIntStateOf(context.resources.configuration.orientation) }

    val configuration = LocalConfiguration.current
    LaunchedEffect(configuration) {
        orientation.intValue = configuration.orientation
    }
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val window = (LocalView.current.context as ComponentActivity).window
    SideEffect {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                show(WindowInsetsCompat.Type.statusBars())
                show(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }


    Scaffold() { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            AndroidView(
                modifier = Modifier
                    .then(if (isLandscape) Modifier.fillMaxHeight() else Modifier.fillMaxWidth())
                    .aspectRatio(16f / 9f)
                    .align(Alignment.Center),
                factory = { factoryContext ->
                    YouTubePlayerView(factoryContext).apply {
                        lifecycleOwner.lifecycle.addObserver(this)

                        enableAutomaticInitialization = false

                        val options = IFramePlayerOptions.Builder(context)
                            .controls(1)
                            .rel(0)
                            .ivLoadPolicy(3)
                            .ccLoadPolicy(0)
                            .build()

                        initialize(object : AbstractYouTubePlayerListener() {
                            override fun onReady(player: YouTubePlayer) {
                                player.loadVideo(youtubeId, playbackPosition)
                            }

                            override fun onCurrentSecond(player: YouTubePlayer, second: Float) {
                                playbackPosition = second
                            }
                        }, options)
                    }
                }
            )

            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close player",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clickable { navController.popBackStack() }
            )
        }
    }

}