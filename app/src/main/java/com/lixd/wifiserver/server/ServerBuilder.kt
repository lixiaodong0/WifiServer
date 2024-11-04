package com.lixd.wifiserver.server

import com.lixd.wifiserver.App

data class ServerBuilder(
    val port: Int,
    val filePath: String,
    val action: List<HttpAction> = mutableListOf(),
)
