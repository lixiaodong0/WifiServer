package com.lixd.wifiserver.server.response

data class FileDescription(
    val name: String,
    val path: String,
    val laseModifyTime: Long,
    val description: String,
    val isFile: Boolean,
    val childCount: Int,
    val parentName: String,
    val parentPath: String,
)

