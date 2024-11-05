package com.lixd.wifiserver.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lixd.wifiserver.navigation.AppNavHost


//通过compositionLocal进行隐私参数传递，每个子组件可以通过LocalShareViewModel访问
val LocalShareViewModel =
    compositionLocalOf<ShareViewModel> { error("请先初始化ShareViewModel!!!") }
val LocalNavController =
    compositionLocalOf<NavHostController> { error("请先初始化NavHostController!!!") }


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiServerApp(modifier: Modifier = Modifier, shareViewModel: ShareViewModel = viewModel()) {
    val navController = rememberNavController()
    //注入
    CompositionLocalProvider(
        LocalShareViewModel provides shareViewModel,
        LocalNavController provides navController,
    ) {
        Scaffold(modifier, topBar = { }, bottomBar = { }) {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val shareViewModel = LocalShareViewModel.current
    val navController = LocalNavController.current
    AppNavHost(navController, shareViewModel)
}