package com.example.bt3.model

enum class LoadingState {
    IDLE,
    LOADING,
    COMPLETED,
    ERROR
}

data class ImageItem(
    val id: Int,
    val url: String,
    val title: String,
    var loadingState: LoadingState = LoadingState.IDLE
)