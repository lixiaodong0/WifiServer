package com.lixd.wifiserver

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SDCardUtils
import com.lixd.wifiserver.server.HttpServerManager
import com.lixd.wifiserver.ui.WifiServerApp
import com.lixd.wifiserver.ui.theme.WifiServerTheme
import com.lixd.wifiserver.util.FileUtil


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val isExternalStorageManager = Environment.isExternalStorageManager()
            if (!isExternalStorageManager) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.setData(Uri.parse("package:$packageName"))
                startActivityForResult(intent, 100)
            }
        }
        Log.d("lixd", "getIPAddress:" + NetworkUtils.getIPAddress(true))
        Log.d("lixd", "getIpAddressByWifi:" + NetworkUtils.getIpAddressByWifi())
        Log.d("lixd", "getGatewayByWifi:" + NetworkUtils.getGatewayByWifi())
        Log.d("lixd", "getNetMaskByWifi:" + NetworkUtils.getNetMaskByWifi())
        Log.d("lixd", "getServerAddressByWifi:" + NetworkUtils.getServerAddressByWifi())

        Log.d(
            "lixd",
            "getExternalTotalSize:" + SDCardUtils.getExternalTotalSize() + ",${
                FileUtil.formatFileSize(SDCardUtils.getExternalTotalSize())
            }"
        )
        Log.d(
            "lixd",
            "getExternalAvailableSize:" + SDCardUtils.getExternalAvailableSize() + ",${
                FileUtil.formatFileSize(SDCardUtils.getExternalAvailableSize())
            }"
        )
        Log.d(
            "lixd",
            "getInternalTotalSize:" + SDCardUtils.getInternalTotalSize() + ",${
                FileUtil.formatFileSize(SDCardUtils.getInternalTotalSize())
            }"
        )
        Log.d(
            "lixd",
            "getInternalAvailableSize:" + SDCardUtils.getInternalAvailableSize() + ",${
                FileUtil.formatFileSize(SDCardUtils.getInternalAvailableSize())
            }"
        )
        setContent {
            WifiServerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WifiServerApp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WifiServerTheme {
        Greeting("Android")
    }
}