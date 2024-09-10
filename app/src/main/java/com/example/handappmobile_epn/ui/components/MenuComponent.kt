@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun MenuLateralScreen(bluetoothConnectionManager: BluetoothConnectionManager) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MenuLateralContent(navController, drawerState) },
        content = {
            MenuScaffoldContent(navController, drawerState, bluetoothConnectionManager)
        }
    )
}

@Composable
fun MenuLateralContent(navController: NavController, drawerState: DrawerState) {
    val items: List<AppScreens> = listOf(
        AppScreens.HomeScreen,
        AppScreens.DevicesScreen,
        AppScreens.TutorialScreen,
        AppScreens.SettingsScreen,
        AppScreens.AboutScreen
    )
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var mostrarPantallaTutorial by remember { mutableStateOf(false) }
    if (mostrarPantallaTutorial) {
        PantallaTutorial(onDismiss = { mostrarPantallaTutorial = false })
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

        items.forEachIndexed() { index, item ->
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    scope.launch {
                        drawerState.close()

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
                        // Se agrega una funcionalidad exclusiva para detectar la ventana tutorial
                        if (route == AppScreens.TutorialScreen.route) {
                            mostrarPantallaTutorial = true
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

@Composable
fun MenuScaffoldContent(
    navController: NavHostController,
    drawerState: DrawerState,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { MenuToolBar(drawerState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding))
        {
            AppNavigation(navController, bluetoothConnectionManager)
        }
    }
}

@Preview
@Composable
fun PruebaPreview() {
    MenuScaffoldContent(
        navController = rememberNavController(),
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        bluetoothConnectionManager = BluetoothConnectionManager(null)
    )
}

@Composable
fun MenuToolBar(
    drawerState: DrawerState)
{
    var showMenuRight by remember { mutableStateOf(false) }
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
            IconButton(onClick = { showMenuRight = !showMenuRight }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }

            DropdownMenu(
                expanded = showMenuRight,
                onDismissRequest = { showMenuRight = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Ajustes") },
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text("Actualizar") },
                    onClick = { /*TODO*/ }
                )
            }
        }
    )
}