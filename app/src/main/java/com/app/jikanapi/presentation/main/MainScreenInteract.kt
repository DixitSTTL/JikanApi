package com.app.jikanapi.presentation.main

import com.app.jikanapi.data.model.AnimeEntity

sealed interface MainScreenInteract {
    class navigateImagePreview(var data: AnimeEntity, var index: String) : MainScreenInteract

}