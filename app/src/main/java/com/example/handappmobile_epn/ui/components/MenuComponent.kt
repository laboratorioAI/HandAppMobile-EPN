@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.navigation.AppNavigation
import com.example.handappmobile_epn.navigation.AppScreens
import com.example.handappmobile_epn.ui.screen.PantallaTutorial
import com.example.handappmobile_epn.utils.DebugSettings
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Pantalla del menú lateral.
 *
 * Esta función crea la interfaz de usuario para el menú lateral de la aplicación,
 * permitiendo la navegación entre diferentes pantallas a través de un controlador de navegación.
 *
 * @param bluetoothConnectionManager Maneja la conexión Bluetooth para la aplicación.
 */
@Composable
fun MenuLateralScreen(bluetoothConnectionManager: BluetoothConnectionManager) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MenuLateralContent(navController, drawerState) },
        content = {
            MenuScaffoldContent(navController, drawerState, bluetoothConnectionManager)
        }
    )
}

/**
 * Contenido del menú lateral.
 *
 * Esta función define los elementos que se mostrarán en el menú lateral,
 * incluyendo las pantallas disponibles para la navegación y su correspondiente lógica.
 *
 * @param navController Controlador de navegación para gestionar la navegación entre pantallas.
 * @param drawerState Estado del cajón del menú lateral.
 */
@Composable
fun MenuLateralContent(navController: NavController, drawerState: DrawerState) {
    val items: List<AppScreens> = listOf(
        AppScreens.HomeScreen,
        AppScreens.DevicesScreen,
        if (DebugSettings.isDebugModeOn) AppScreens.DebugScreen else null,
        AppScreens.SettingsScreen,
        AppScreens.AboutScreen
    ).filterNotNull()
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    // Crear el controlador para modificar las barras de sistema
    val systemUiController = rememberSystemUiController()

    // Cambiar el color de la barra de estado
    systemUiController.setStatusBarColor(
        color = colorResource(id = R.color.app_dark_bg), // Color de la barra de estado
        darkIcons = false // No se usan iconos oscuros
    )

    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerContainerColor = colorResource(id = R.color.app_basic_bg)
    )
    {
        MenuLateralHeader()

        Spacer(modifier = Modifier.height(16.dp))

        items.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    scope.launch {
                        drawerState.close()

                        // Lógica de navegación - Navegar a la pantalla correspondiente de acuerdo a la ruta definida en navigation/AppScreens.kt
                        val route = item.route
                        if (navController.currentBackStackEntry?.destination?.route != route) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = if (index == selectedItemIndex) painterResource(id = item.selectedIcon)
                        else painterResource(id = item.unselectedIcon),
                        contentDescription = item.title
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0x0F000000),
                    selectedIconColor = colorResource(id = R.color.black),
                    selectedTextColor = colorResource(id = R.color.black),
                    unselectedContainerColor = colorResource(id = R.color.app_basic_bg),
                    unselectedIconColor = colorResource(id = R.color.black),
                    unselectedTextColor = colorResource(id = R.color.black)
                )

            )
        }
    }
}

/**
 * Encabezado del menú lateral.
 *
 * Esta función crea la parte superior del menú lateral,
 * que incluye el logo y el título de la aplicación.
 */
@Composable
fun MenuLateralHeader() {
    val logo = painterResource(id = R.drawable.logocircular)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.app_dark))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = logo,
            contentDescription = "App Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Hand App",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }

}

/**
 * Contenido del scaffold del menú.
 *
 * Esta función proporciona la estructura básica para la pantalla principal,
 * incluyendo la barra superior y el contenido de navegación de la aplicación.
 *
 * @param navController Controlador de navegación.
 * @param drawerState Estado del cajón del menú lateral.
 * @param bluetoothConnectionManager Maneja la conexión Bluetooth para la aplicación.
 */
@Composable
fun MenuScaffoldContent(
    navController: NavHostController,
    drawerState: DrawerState,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { MenuToolBar(drawerState) }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
        {
            AppNavigation(navController, bluetoothConnectionManager)
        }
    }
}

/**
 * Barra superior del menú.
 *
 * Esta función crea la barra superior que contiene el título de la aplicación
 * y los iconos de navegación, permitiendo abrir el menú lateral y mostrar un tutorial.
 *
 * @param drawerState Estado del cajón del menú lateral.
 */
@Composable
fun MenuToolBar(
    drawerState: DrawerState)
{
    var mostrarPantallaTutorial by remember { mutableStateOf(false) }
    if (mostrarPantallaTutorial) {
        PantallaTutorial(onDismiss = { mostrarPantallaTutorial = false })
    }

    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = "Hand App") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.app_primary),
            navigationIconContentColor = colorResource(id = R.color.white),
            titleContentColor = colorResource(id = R.color.white),
            actionIconContentColor = colorResource(id = R.color.white)
        ),
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { mostrarPantallaTutorial = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.app_unselected_tutorial),
                    contentDescription = "Tutorial"
                )
            }
        }
    )
}