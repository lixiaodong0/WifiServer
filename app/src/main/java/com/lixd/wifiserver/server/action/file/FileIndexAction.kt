package com.lixd.wifiserver.server.action.file

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.App
import com.lixd.wifiserver.server.HttpGetAction

/**
 *
 */
class FileIndexAction : HttpGetAction("/files") {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        val input = App.appContext.resources.assets.open("files.html")
        response.sendStream(input, input.available().toLong())
    }
}