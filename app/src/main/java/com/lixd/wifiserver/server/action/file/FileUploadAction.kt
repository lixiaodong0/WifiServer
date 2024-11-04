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
class FileUploadAction : HttpGetAction("/files/upload") {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        //解决跨域问题
        response.headers.add("Access-Control-Allow-Origin", "*")
        val toPath = request.query.getString("toPath")
        val fileName = request.query.getString("fileName")
        if (toPath.isNullOrEmpty() || fileName.isNullOrEmpty()) {
            response.send(
                BaseResponse.failure(errMsg = "目录路径或文件名称不能为空，请确认后在重新上传")
                    .toJsonObject()
            )
            return
        }
        val file = File(toPath)
        if (!file.exists()) {
            response.send(BaseResponse.failure(errMsg = "目录路径不存在").toJsonObject())
            return
        }
        if (file.isFile) {
            response.send(BaseResponse.failure(errMsg = "目录路径不合法").toJsonObject())
            return
        }
    }
}