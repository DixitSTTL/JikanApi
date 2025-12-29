package com.app.jikanapi.presentation.animdetail

import androidx.lifecycle.viewModelScope
import com.app.jikanapi.data.model.AnimDetailDTO
import com.app.jikanapi.data.utils.ResponseResult
import com.app.jikanapi.domain.usecase.UseCaseAnimDetailScreen
import com.app.jikanapi.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimDetailScreenViewModel(
    private var useCaseAnimDetailScreen: UseCaseAnimDetailScreen,
    private var animId: String
) : BaseViewModel<AnimDetailScreenState, AnimDetailScreenInteract>(AnimDetailScreenState()) {

    init {
        fetchCommon()
    }

    private fun fetchCommon() {
        viewModelScope.launch(Dispatchers.IO) {
            setDataState(getStateData().copy(isLoading = true))
            val responseFlowImage =
                useCaseAnimDetailScreen.fetchAnimDetail(animId)
            when (responseFlowImage) {
                is ResponseResult.Error<*> -> {
                    val responseFlowAnim =
                        useCaseAnimDetailScreen.fetchAnimDetailFromDatabase(animId)
                    responseFlowAnim?.let {
                        setDataState(
                            getStateData().copy(
                                isLoading = false,
                                animData = it,
                            )
                        )
                    }
                }

                is ResponseResult.Success<*> -> {
                    setDataState(
                        getStateData().copy(
                            isLoading = false,
                            animData = (responseFlowImage.data as AnimDetailDTO).data.toEntity(),
                        )
                    )
                }
            }

        }
    }


}