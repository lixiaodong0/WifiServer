package com.lixd.wifiserver

import android.app.Application
import android.content.Context
import com.lixd.wifiserver.server.HttpServerImpl
import com.lixd.wifiserver.server.HttpServerManager
import com.lixd.wifiserver.server.ServerBuilder
import com.lixd.wifiserver.server.action.IndexAction
import com.lixd.wifiserver.server.action.file.FileDownloadAction
import com.lixd.wifiserver.server.action.file.FileGetAction
import com.lixd.wifiserver.server.action.file.FileIndexAction
import com.lixd.wifiserver.server.action.file.FileUploadAction

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initServer()
    }

    private fun initServer() {
        HttpServerManager.instance.run {
            init(
                ServerBuilder(
                    8080, appContext.getExternalFilesDir("")!!.absolutePath, mutableListOf(
                        IndexAction(),
                        FileIndexAction(),
                        FileGetAction(),
                        FileDownloadAction(),
                        FileUploadAction(),
                    )
                ), HttpServerImpl()
            )
            start()
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}