package com.lixd.wifiserver.server

class HttpServerManager private constructor() {
    private lateinit var serverBuilder: ServerBuilder
    private lateinit var httpServerImpl: HttpServerImpl
    private var isInit = false

    val port: Int by lazy {
        serverBuilder.port
    }
    val filePath: String by lazy {
        serverBuilder.filePath
    }

    fun init(serverBuilder: ServerBuilder, httpServerImpl: HttpServerImpl) {
        this.serverBuilder = serverBuilder
        this.httpServerImpl = httpServerImpl
        isInit = true
        httpServerImpl.initActions(serverBuilder.action)
    }

    fun start() {
        if (!isInit) {
            throw RuntimeException("请先调用Init()方法进行初始化")
        }
        httpServerImpl.start(serverBuilder.port)
    }

    fun stop() {
        if (!isInit) {
            throw RuntimeException("请先调用Init()方法进行初始化")
        }
        httpServerImpl.stop()
    }


    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpServerManager()
        }
    }
}