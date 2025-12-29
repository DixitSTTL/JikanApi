package com.app.jikanapi.presentation.main

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.jikanapi.common.AnimList
import com.app.jikanapi.domain.routes
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainScreen(
    navController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: MainScreenViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val stateImageFlow = state.data.imageFlowList.collectAsLazyPagingItems()


    LaunchedEffect(Unit) {
        viewModel.uiAction.collectLatest {
            when (it) {
                is MainScreenInteract.navigateImagePreview -> {
//                    val data = Uri.encode(Gson().toJson(it.data))
                    navController.navigate("${routes.ANIM_DETAIL_SCREEN}/${it.data.malId}")
                }

            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text("Anim List")
                }

            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize() // 🔥 THIS IS MISSING
                .padding(top = innerPadding.calculateTopPadding())
//                .background(color = Theme.colors.background)
        )
        {
            AnimList(
                stateImageFlow,
                onClick = { it, index ->
                    viewModel.sendAction(
                        MainScreenInteract.navigateImagePreview(
                            it,
                            index
                        )
                    )
                }, animatedVisibilityScope
            )
        }
    }

}