package com.example.handappmobile_epn.navigation

import com.example.handappmobile_epn.R

sealed class AppScreens(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String
) {
    object HomeScreen : AppScreens("Inicio", R.drawable.app_selected_home, R.drawable.app_unselected_home, "home_screen")
    object DevicesScreen : AppScreens("Dispositivos", R.drawable.app_selected_bt, R.drawable.app_unselected_bt, "devices_screen")
    object TutorialScreen : AppScreens("Tutorial", R.drawable.app_selected_tutorial, R.drawable.app_unselected_tutorial, "tutorial_screen")
    object SettingsScreen : AppScreens("Ajustes", R.drawable.app_selected_settings, R.drawable.app_unselected_settings, "settings_screen")
    object AboutScreen : AppScreens("Acerca de", R.drawable.app_selected_about, R.drawable.app_unselected_about, "about_screen")
}