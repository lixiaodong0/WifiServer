package com.lixd.wifiserver.util

import java.net.URLDecoder


fun ByteArray.urlDecodeString(): String {
    return URLDecoder.decode(String(this), "UTF-8")
}
fun String.urlDecode(byteArray: ByteArray): String {
    return URLDecoder.decode(String(byteArray), "UTF-8")
}

