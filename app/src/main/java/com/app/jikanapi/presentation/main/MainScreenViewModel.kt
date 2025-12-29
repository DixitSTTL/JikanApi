package com.app.jikanapi.presentation.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.app.jikanapi.domain.usecase.UseCaseMainScreen
import com.app.jikanapi.presentation.base.BaseViewModel

class MainScreenViewModel(
    private var useCaseMainScreen: UseCaseMainScreen,
) : BaseViewModel<MainScreenState, MainScreenInteract>(MainScreenState()) {

    init {
        fetchCommon()
    }

    private fun fetchCommon() {
        setDataState(getStateData().copy(isLoading = true))
        val responseFlowImage =
            useCaseMainScreen.fetchFlowImage().cachedIn(viewModelScope)

        setDataState(
            getStateData().copy(
                isLoading = false,
                imageFlowList = responseFlowImage,
            )
        )
    }

}