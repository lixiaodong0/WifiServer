package com.lixd.wifiserver.server.action.file

import android.os.Environment
import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.server.HttpGetAction
import com.lixd.wifiserver.server.response.BaseResponse
import com.lixd.wifiserver.server.response.toJsonObject
import com.lixd.wifiserver.util.FileUtil
import java.io.File


val externalStoragePath = Environment.getExternalStorageDirectory().absolutePath

/**
 * 创建日期：2024/10/31 16:23
 * @author lixiaodong
 * @version 1.0
 * 类说明：
 */
class FileGetAction : HttpGetAction("/files/get") {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        //解决跨域问题
        response.headers.add("Access-Control-Allow-Origin", "*")
        val selectDirPath = request.query.getString("selectDirPath")?.ifEmpty {
            externalStoragePath
        } ?: externalStoragePath
        val selectDirFile = File(selectDirPath)
        Log.d("GetFilesAction", "selectDirFile:${selectDirFile.absolutePath}")
        val files = FileUtil.getChildFiles(selectDirFile)
        val jsonObject = BaseResponse.success(files).toJsonObject()
        Log.d("GetFilesAction", jsonObject.toString())
        response.send(jsonObject)
    }
}

