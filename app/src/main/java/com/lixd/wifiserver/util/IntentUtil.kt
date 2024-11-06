package com.lixd.wifiserver.util

import android.content.Intent
import android.net.Uri
import com.lixd.wifiserver.App

object IntentUtil {
    fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.appContext.startActivity(intent)
    }
}