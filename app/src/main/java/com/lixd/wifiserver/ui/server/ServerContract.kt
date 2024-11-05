package com.lixd.wifiserver.ui.server

import android.graphics.Bitmap


data class ServerUiState(
    val ipAddress: String = "",
    val port: Int = 0,
    val sdCardSizeDescription: String = "",
    val serverSizeDescription: String = "",
    val qrCodeBitmap: Bitmap? = null,
)

sealed class ServerUiAction {
    data object GetData : ServerUiAction()
    data class CreateQRCode(val bitmap: Bitmap) : ServerUiAction()
}

