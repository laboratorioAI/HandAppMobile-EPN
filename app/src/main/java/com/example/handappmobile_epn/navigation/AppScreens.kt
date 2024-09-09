package com.example.handappmobile_epn.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object DevicesScreen : AppScreens("devices_screen")
    object TutorialScreen : AppScreens("tutorial_screen")
    object SettingsScreen : AppScreens("settings_screen")
    object AboutScreen : AppScreens("about_screen")
}