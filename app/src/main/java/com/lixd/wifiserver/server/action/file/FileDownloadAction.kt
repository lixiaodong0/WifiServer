package com.lixd.wifiserver.server.action.file

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.server.HttpGetAction
import com.lixd.wifiserver.server.response.BaseResponse
import com.lixd.wifiserver.server.response.toJsonObject
import java.io.File

/**
 * 创建日期：2024/11/2 21:55
 * @author lixiaodong
 * @version 1.0
 * 类说明：
 */
class FileDownloadAction : HttpGetAction("/files/download") {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        //解决跨域问题
        response.headers.add("Access-Control-Allow-Origin", "*")
        val selectFile = request.query.getString("selectFile")
        if (selectFile.isNullOrEmpty()) {
            response.send(BaseResponse.failure(errMsg = "请选择文件").toJsonObject())
            return
        }
        val file = File(selectFile)
        if (!file.exists()) {
            response.send(BaseResponse.failure(errMsg = "文件不存在").toJsonObject())
            return
        }
        if (!file.isFile) {
            response.send(BaseResponse.failure(errMsg = "不支持文件夹").toJsonObject())
            return
        }
        val input = file.inputStream()
        response.sendStream(input, input.available().toLong())
        input.close()
    }
}