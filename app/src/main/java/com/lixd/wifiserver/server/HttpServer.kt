package com.lixd.wifiserver.server

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse

interface HttpServer {
    /**
     * 开启服务
     * @param port 端口号
     */
    fun start(port: Int)

    /**
     * 停止服务
     */
    fun stop()

    fun initActions(actions: List<HttpAction>)
}

interface HttpAction {
    fun onGet(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse)
    fun onPost(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse)
    fun method(): HttpActionMethod
}

sealed class HttpActionMethod(val path: String) {
    class Get(path: String) : HttpActionMethod(path)
    class Post(path: String) : HttpActionMethod(path)
}
