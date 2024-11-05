package com.lixd.wifiserver.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lixd.wifiserver.ui.ShareViewModel
import com.lixd.wifiserver.ui.server.ServerScreen

@Composable
fun AppNavHost(navController: NavHostController, shareViewModel: ShareViewModel) {
    NavHost(navController = navController, startDestination = AppRoutes.HOME) {
        composable(AppRoutes.HOME) {
            ServerScreen()
        }
    }
}