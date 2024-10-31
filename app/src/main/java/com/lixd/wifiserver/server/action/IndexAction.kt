package com.lixd.wifiserver.server.action

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.App
import com.lixd.wifiserver.server.HttpAction
import com.lixd.wifiserver.server.HttpActionMethod

class IndexAction : HttpAction {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {
        val input = App.appContext.resources.assets.open("index.html")
        response.sendStream(input, input.available().toLong())
    }

    override fun onPost(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {

    }

    override fun method(): HttpActionMethod = HttpActionMethod.Get("/")
}