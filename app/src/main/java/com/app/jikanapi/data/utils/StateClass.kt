package com.app.jikanapi.data.utils

data class StateClass<T>(
    var isLoading: Boolean,
    var data: T
)