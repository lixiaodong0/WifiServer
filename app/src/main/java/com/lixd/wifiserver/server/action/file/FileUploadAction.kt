package com.lixd.wifiserver.server.action.file

import android.os.Build
import android.util.Log
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.koushikdutta.async.http.body.MultipartFormDataBody
import com.koushikdutta.async.http.body.StreamBody
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.App
import com.lixd.wifiserver.server.HttpGetAction
import com.lixd.wifiserver.server.HttpPostAction
import com.lixd.wifiserver.server.HttpServerManager
import com.lixd.wifiserver.server.response.BaseResponse
import com.lixd.wifiserver.server.response.toJsonObject
import com.lixd.wifiserver.util.md5
import com.lixd.wifiserver.util.urlDecodeString
import java.io.File
import java.io.FileOutputStream
import java.io.FilterOutputStream
import java.net.URLDecoder
import java.util.LinkedList

/**
 * 创建日期：2024/11/2 21:55
 * @author lixiaodong
 * @version 1.0
 * 类说明：
 */
class FileUploadAction : HttpPostAction("/files/upload") {
    override fun onPost(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        //解决跨域问题
        response.headers.add("Access-Control-Allow-Origin", "*")
        val extendInfoMap = HashMap<String, String>()
        val fileByteArrayInfoMap = HashMap<String, LinkedList<ByteArray>>()
        Log.d(TAG, "request-body:${request.body}")
        val multipartFormDataBody = request.body as? MultipartFormDataBody
        multipartFormDataBody?.run {
            setMultipartCallback {
                if (it.isFile) {
                    setDataCallback { emitter, bb ->
                        val allByteArray = bb.allByteArray
                        Log.d(TAG, "[DataCallback]name:${it.name},size:${allByteArray.size}")
                        val linkedList = fileByteArrayInfoMap[it.filename] ?: LinkedList()
                        linkedList.add(allByteArray)
                        fileByteArrayInfoMap[it.filename] = linkedList
                        bb.recycle()
                    }
                } else {
                    setDataCallback { emitter, bb ->
                        val allByteArray = bb.allByteArray
                        Log.d(TAG, "[DataCallback]name:${it.name},size:${allByteArray.size}")
                        extendInfoMap[it.name] = allByteArray.urlDecodeString()
                        bb.recycle()
                    }
                }
            }
        }
        request.setEndCallback {
            Log.d(TAG, "[RequestEnd]extendInfoMap:${extendInfoMap}")
            Log.d(TAG, "[RequestEnd]fileByteArrayInfoMapSize:${fileByteArrayInfoMap.size}")

            if (extendInfoMap.isEmpty()) {
                response.send(
                    BaseResponse.failure(errMsg = "上传的文件是空的，请确认后重新上传")
                        .toJsonObject()
                )
                return@setEndCallback
            }

            val defaultToPath = HttpServerManager.instance.filePath
            val toPath = extendInfoMap[REQUEST_FIELD_TO_PATH]?.ifEmpty {
                defaultToPath
            } ?: defaultToPath
            val uploadFileName = extendInfoMap[REQUEST_FIELD_FILE_NAME] ?: ""

            val fileMD5 = extendInfoMap[REQUEST_FIELD_FILE_MD5] ?: ""
            var baseResponse = BaseResponse.success("ok")
            fileByteArrayInfoMap.forEach { (fileName, fileByteArray) ->
                val finalFileName = uploadFileName.ifEmpty { fileName }
                val file = File(toPath, finalFileName)
                if (file.parentFile?.exists() == false) {
                    file.parentFile?.mkdirs()

                }
                FileOutputStream(file).use {
                    fileByteArray.forEach { byteArray ->
                        it.write(byteArray)
                    }
                    it.flush()
                    Log.d(TAG, "文件保存成功:${file.absolutePath}")
                }
                val currentFileMd5 = file.md5()
                Log.d(TAG, "fileMD5:${fileMD5},currentFileMd5:${currentFileMd5}")
                if (fileMD5.equals(currentFileMd5, true)) {
                    Log.d(TAG, "md5校验成功")
                    baseResponse = BaseResponse.success("success")
                } else {
                    Log.d(TAG, "md5校验失败")
                    baseResponse = BaseResponse.failure(-1, "md5校验失败")
                }
            }
            response.send(baseResponse.toJsonObject())
            response.end()
        }
    }

    companion object {
        private const val TAG = "FileUploadAction"
        private const val REQUEST_FIELD_FILE_NAME = "fileName"
        private const val REQUEST_FIELD_FILE_MD5 = "fileMd5"
        private const val REQUEST_FIELD_TO_PATH = "toPath"
    }
}