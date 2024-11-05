package com.lixd.wifiserver.ui.server

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SDCardUtils
import com.lixd.wifiserver.server.HttpServerManager
import com.lixd.wifiserver.util.FileUtil
import com.lixd.wifiserver.util.QRCodeGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServerUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: ServerUiAction) {
        when (action) {
            is ServerUiAction.GetData -> {
                initData()
            }

            is ServerUiAction.CreateQRCode -> {
                createQRCode()
            }

            else -> {}
        }
    }

    private fun createQRCode() {
        viewModelScope.launch {
            val ipAddress = NetworkUtils.getIpAddressByWifi()
            val port = HttpServerManager.instance.port
            val content = "$ipAddress:$port"
            val qrCodeImage = QRCodeGenerator.generateQrCodeImage(content)
            _uiState.update {
                it.copy(
                    qrCodeBitmap = qrCodeImage
                )
            }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            val ipAddress = NetworkUtils.getIpAddressByWifi()
            val port = HttpServerManager.instance.port
            val externalTotalSize = SDCardUtils.getExternalTotalSize()
            val externalAvailableSize = SDCardUtils.getExternalAvailableSize()
            val sdCardSizeDescription = "${FileUtil.formatFileSize(externalAvailableSize)} 可用/${
                FileUtil.formatFileSize(externalTotalSize)
            }"
            _uiState.update {
                it.copy(
                    ipAddress = ipAddress,
                    port = port,
                    sdCardSizeDescription = sdCardSizeDescription
                )
            }
            createQRCode()
        }
    }
}