package com.lixd.wifiserver.ui.server

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blankj.utilcode.util.NetworkUtils

@Composable
fun ServerScreen(viewModel: ServerViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit, block = {
        viewModel.onAction(ServerUiAction.GetData)
    })
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            ServerInfoContainer(uiState.ipAddress, uiState.port, uiState.sdCardSizeDescription)
            Spacer(modifier = Modifier.size(20.dp))
            ConnectionMethodContainer(uiState.ipAddress, uiState.port, uiState.qrCodeBitmap)
        }
    }
}


@Composable
fun ServerInfoContainer(ipAddress: String, port: Int, sdCardSizeDescription: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "服务器：",
            fontSize = 30.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "IP地址：${ipAddress}")
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "端口：${port}")
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "存储容量：${sdCardSizeDescription}")
    }
}

@Composable
fun ConnectionMethodContainer(ipAddress: String, port: Int, qrCodeBitmap: Bitmap?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "连接方式：",
            fontSize = 30.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "方式1：打开浏览器输入${ipAddress}:${port}")
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "方式2：打开浏览器扫描二维码")
        Spacer(modifier = Modifier.size(20.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (qrCodeBitmap != null) {
                Image(
                    bitmap = qrCodeBitmap.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ServerScreenPreview() {
    ServerScreen()
}