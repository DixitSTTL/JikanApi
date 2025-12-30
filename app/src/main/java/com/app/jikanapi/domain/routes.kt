package com.app.jikanapi.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class routes(val route: String) {

    @Serializable
    data object MAIN_SCREEN : routes("MAIN_SCREEN")

    @Serializable
    data object ANIM_DETAIL_SCREEN : routes("ANIM_DETAIL_SCREEN/{animId}")

    @Serializable
    data object YOUTUBE_SCREEN : routes("YOUTUBE_SCREEN/{youtubeId}")


}