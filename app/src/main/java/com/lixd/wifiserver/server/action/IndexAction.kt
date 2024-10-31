package com.lixd.wifiserver.server.action

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.lixd.wifiserver.server.HttpAction
import com.lixd.wifiserver.server.HttpActionMethod

class IndexAction : HttpAction {
    override fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {

    }

    override fun onPost(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) {

    }

    override fun method(): HttpActionMethod = HttpActionMethod.Get("/")
}