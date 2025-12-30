package com.app.jikanapi.data.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri

object Utils {
    fun Context.isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    fun String.extractYouTubeId(): String? {
        val regex = Regex(
            "(?:(?:youtube(?:-nocookie)?\\.com\\/(?:embed\\/|watch\\?v=|v\\/))|(?:youtu\\.be\\/))([A-Za-z0-9_-]{11})"
        )
        return regex.find(this)?.groupValues?.get(1)
    }

    fun Context.openYoutube(videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
            .apply {
                if (resolveActivity(packageManager) == null) {
                    data = Uri.parse("https://www.youtube.com/watch?v=$videoId")
                }
            }
        startActivity(intent)
    }

}