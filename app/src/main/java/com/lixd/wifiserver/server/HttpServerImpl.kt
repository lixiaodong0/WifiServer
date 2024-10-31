package com.lixd.wifiserver.server

import android.util.Log
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import com.lixd.wifiserver.App

class HttpServerImpl : HttpServer {
    private val server: AsyncHttpServer = AsyncHttpServer()
    private val getActions = mutableMapOf<String, HttpAction>()
    private val postActions = mutableMapOf<String, HttpAction>()

    private val serverRequestCallback =
        HttpServerRequestCallback { request, response ->
            Log.d("${TAG}[request]path:", request.path)
            Log.d("${TAG}[request]headers:", request.headers.toString())
            Log.d("${TAG}[request]matcher:", request.matcher.toString())
            Log.d("${TAG}[request]method:", request.method)

            val input = App.appContext.resources.assets.open("index.html")
            response.sendStream(input, input.available().toLong())
        }

    /**
     * 启动服务
     */
    override fun start(port: Int) {
        Log.e(TAG, "[start]服务已开启")
        server.get("/", serverRequestCallback)
        server.listen(PORT)
    }

    /**
     * 停止服务
     */
    override fun stop() {
        Log.e(TAG, "[stop(]服务已停止")
        server.stop()
    }

    override fun initActions(actions: List<HttpAction>) {
        actions.forEach {
            val actionMethod = it.method()
            if (actionMethod is HttpActionMethod.Get) {
                getActions[actionMethod.path] = it
            } else {
                postActions[actionMethod.path] = it
            }
        }
        getActions.forEach { (key, _) ->
            server.get(key, serverRequestCallback)
        }
        postActions.forEach { (key, _) ->
            server.post(key, serverRequestCallback)
        }
    }

    companion object {
        private const val TAG = "HttpServer"
        const val PORT = 8080

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpServerImpl()
        }
    }
}