package com.app.jikanapi.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.jikanapi.common.AnimList
import com.app.jikanapi.domain.routes
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val stateAnimListFlow = viewModel.animFlowList.collectAsLazyPagingItems()


    LaunchedEffect(Unit) {
        viewModel.uiAction.collectLatest {
            when (it) {
                is MainScreenInteract.navigateAnimDetail -> {
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
            if (stateAnimListFlow.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {

                AnimList(
                    stateAnimListFlow,
                    onClick = { it, index ->
                        viewModel.sendAction(
                            MainScreenInteract.navigateAnimDetail(
                                it,
                            )
                        )
                    }
                )
            }
        }
    }

}