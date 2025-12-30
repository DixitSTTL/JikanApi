package com.app.jikanapi.presentation.main

import com.app.jikanapi.data.model.AnimeEntity

sealed interface MainScreenInteract {
    class navigateAnimDetail(var data: AnimeEntity) : MainScreenInteract

}