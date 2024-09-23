package com.example.handappmobile_epn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.screen.AboutScreen
import com.example.handappmobile_epn.ui.screen.DebugScreen
import com.example.handappmobile_epn.ui.screen.DevicesScreen
import com.example.handappmobile_epn.ui.screen.HomeScreen
import com.example.handappmobile_epn.ui.screen.SettingsScreen

@Composable
fun AppNavigation(navController: NavHostController, bluetoothConnectionManager: BluetoothConnectionManager) {
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(AppScreens.HomeScreen.route) { HomeScreen(bluetoothConnectionManager) }
        composable(AppScreens.DevicesScreen.route) { DevicesScreen(bluetoothConnectionManager) }
        composable(AppScreens.DebugScreen.route) { DebugScreen(bluetoothConnectionManager) }
        composable(AppScreens.SettingsScreen.route) { SettingsScreen() }
        composable(AppScreens.AboutScreen.route) { AboutScreen() }
    }
}