package com.app.jikanapi.presentation.animdetail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimDetailScreen(
    navController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    animId: String,
    viewModel: AnimDetailScreenViewModel = koinViewModel(parameters = { parametersOf(animId) })
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value.data


    LaunchedEffect(Unit) {

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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(4.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "${animId}_image"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                zIndexInOverlay = 2F,
                            )
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.animData?.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.height(30.dp))
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

                        )
                }
            }


        }
    }

}