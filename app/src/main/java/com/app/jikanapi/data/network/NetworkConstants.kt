package com.app.jikanapi.data.network

object NetworkConstants {

    private fun getBaseURL() = "api.jikan.moe/v4"
    val BASE_URL = getBaseURL()

    //route
    val topAnim = "top/anime"
    val animDetail = "anime/"

}