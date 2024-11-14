package com.example.handappmobile_epn.navigation

import com.example.handappmobile_epn.R

/**
 * Representa las pantallas de la aplicación, cada una con un título, íconos de selección y una ruta de navegación.
 *
 * @property title El título de la pantalla.
 * @property selectedIcon El recurso de ícono que se muestra cuando la pantalla está seleccionada.
 * @property unselectedIcon El recurso de ícono que se muestra cuando la pantalla no está seleccionada.
 * @property route La ruta de navegación asociada con la pantalla.
 */
sealed class AppScreens(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String
) {
    object HomeScreen : AppScreens("Inicio", R.drawable.app_selected_home, R.drawable.app_unselected_home, "home_screen")
    object DevicesScreen : AppScreens("Dispositivos", R.drawable.app_selected_bt, R.drawable.app_unselected_bt, "devices_screen")
    object DebugScreen : AppScreens("Depuración", R.drawable.app_selected_debug, R.drawable.app_unselected_debug, "debug_screen")
    object SettingsScreen : AppScreens("Ajustes", R.drawable.app_selected_settings, R.drawable.app_unselected_settings, "settings_screen")
    object AboutScreen : AppScreens("Acerca de", R.drawable.app_selected_about, R.drawable.app_unselected_about, "about_screen")
}