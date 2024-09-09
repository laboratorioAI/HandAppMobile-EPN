package com.example.handappmobile_epn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.screen.DevicesScreen
import com.example.handappmobile_epn.ui.screen.HomeScreen

@Composable
fun AppNavigation(bluetoothConnectionManager: BluetoothConnectionManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(AppScreens.HomeScreen.route) { HomeScreen(navController, bluetoothConnectionManager) }
        composable(AppScreens.DevicesScreen.route) { DevicesScreen(navController, bluetoothConnectionManager) }
    }
}