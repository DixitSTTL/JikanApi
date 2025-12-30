package com.app.jikanapi.presentation.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.jikanapi.data.model.AnimeEntity
import com.app.jikanapi.domain.usecase.UseCaseMainScreen
import com.app.jikanapi.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class MainScreenViewModel(
    private var useCaseMainScreen: UseCaseMainScreen,
) : BaseViewModel<MainScreenState, MainScreenInteract>(MainScreenState()) {

    init {
//        fetchCommon()
    }

    val animFlowList: Flow<PagingData<AnimeEntity>> =
        useCaseMainScreen.fetchFlowAnim()
            .cachedIn(viewModelScope)

    private fun fetchCommon() {
        val responseFlowAnim =
            useCaseMainScreen.fetchFlowAnim().cachedIn(viewModelScope)

        setDataState(
            getStateData().copy(
                isLoading = false,
                animFlowList = responseFlowAnim,
            )
        )
    }

}