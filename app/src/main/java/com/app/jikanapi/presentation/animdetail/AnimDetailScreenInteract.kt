package com.app.jikanapi.presentation.animdetail

sealed interface AnimDetailScreenInteract {
    class viewYoutubeTrailer(var youtubeId: String) : AnimDetailScreenInteract
    class navigateYoutubeScreen(var youtubeId: String) : AnimDetailScreenInteract
}