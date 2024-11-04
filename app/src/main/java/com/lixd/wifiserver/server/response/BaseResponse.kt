package com.lixd.wifiserver.server.response

import com.google.gson.Gson
import org.json.JSONObject

private val gson = Gson()

data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val result: T?
) {
    companion object {
        fun <T> success(data: T?): BaseResponse<T> {
            return BaseResponse(0, "success", data)
        }

        fun failure(code: Int = -1, errMsg: String): BaseResponse<String> {
            return BaseResponse(code, errMsg, "")
        }
    }
}

fun <T> BaseResponse<T>.toJsonObject(): JSONObject {
    val json = gson.toJson(this)
    return JSONObject(json)
}

