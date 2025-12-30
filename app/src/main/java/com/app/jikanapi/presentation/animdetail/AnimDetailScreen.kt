package com.app.jikanapi.presentation.animdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.app.jikanapi.R
import com.app.jikanapi.data.utils.Utils.openYoutube
import com.app.jikanapi.domain.routes
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimDetailScreen(
    navController: NavHostController,
    animId: String,
    viewModel: AnimDetailScreenViewModel = koinViewModel(parameters = { parametersOf(animId) })
) {
    val context = LocalContext.current

    val state = viewModel.state.collectAsStateWithLifecycle().value.data
    var imageLoaded by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.uiAction.collectLatest {
            when (it) {
                is AnimDetailScreenInteract.viewYoutubeTrailer -> {
                    context.openYoutube(it.youtubeId)
                }

                is AnimDetailScreenInteract.navigateYoutubeScreen -> {
                    navController.navigate("${routes.YOUTUBE_SCREEN}/${it.youtubeId}")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text("Anim Detail")
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        )
        {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {


                    if (state.animData?.imageUrl?.isNotEmpty() == true) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.animData?.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.FillWidth,
                                onSuccess = {
                                    imageLoaded = true
                                },
                                onError = {
                                    imageLoaded = false
                                }
                            )
                        }

                        if (imageLoaded) {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }

                    Text(
                        state.animData?.titleEnglish ?: "",
                        fontSize = 24.sp,
                        fontWeight = FontWeight(700)
                    )

                    if (state.animData?.genres?.isNotEmpty() == true) {
                        Spacer(Modifier.height(18.dp))
                        Text(
                            "Genres",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700)
                        )
                        LazyRow() {
                            items(state.animData?.genres ?: emptyList()) { genres ->

                                Card(
                                    shape = RoundedCornerShape(50.dp),
                                    border = BorderStroke(1.dp, Color.Red),
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        genres ?: "",
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(10.dp, 4.dp)
                                    )
                                }
                            }
                        }
                    }
                    if (state.animData?.studios?.isNotEmpty() == true) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Producer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700)
                        )
                        LazyRow() {
                            items(state.animData?.studios ?: emptyList()) { producers ->

                                Card(
                                    shape = RoundedCornerShape(50.dp),
                                    border = BorderStroke(1.dp, Color.Red),
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        producers ?: "",
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(10.dp, 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (state.animData?.youtubeId?.isNotEmpty() == true) {
                        Spacer(Modifier.height(18.dp))
                        Text(
                            "Watch Trailer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700)
                        )
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .height(90.dp)
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                state.animData?.youtubeId?.let { id ->
                                    Image(
                                        painterResource(R.drawable.ic_youtube),
                                        "",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .zIndex(10f)
                                            .align(
                                                Alignment.Center
                                            )
                                            .clickable(onClick = {
//                                                viewModel.sendAction(
//                                                    AnimDetailScreenInteract.viewYoutubeTrailer(
//                                                        id,
//                                                    )
//                                                )

                                                viewModel.sendAction(
                                                    AnimDetailScreenInteract.navigateYoutubeScreen(
                                                        id,
                                                    )
                                                )
                                            })

                                    )
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("https://img.youtube.com/vi/${id}/hqdefault.jpg")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(
                                                Alignment.Center
                                            ),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        ("Rating: " + state.animData?.rating) ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600)
                    )
                    Text(
                        ("Episodes: " + state.animData?.episodes?.toString()) ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600)
                    )
                    Spacer(Modifier.height(18.dp))
                    Text(
                        state.animData?.synopsis ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.alpha(0.8f)
                    )
                }
            }


        }
    }

}