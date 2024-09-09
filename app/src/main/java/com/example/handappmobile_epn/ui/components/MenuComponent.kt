@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.navigation.AppScreens
import com.example.handappmobile_epn.ui.screen.HomeContentScreen
import kotlinx.coroutines.launch

@Composable
fun HomeDrawerScreen(
    navController: NavController,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { HomeDrawerContent(navController) },
        content = { HomeScaffoldContent(bluetoothConnectionManager = bluetoothConnectionManager, drawerState) }
    )
}

@Composable
fun HomeDrawerContent(navController: NavController) {
    val logo = painterResource(id = R.drawable.logocircular)

    val inicioOnClick = { /*TODO*/ }
    val devicesOnClick = { navController.navigate(AppScreens.DevicesScreen.route) }

    Column(modifier = Modifier
        .background(colorResource(id = R.color.white))
        .fillMaxHeight()
        .width(300.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.app_dark))
                .padding(16.dp)
                .padding(
                    top = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateTopPadding()
                ),
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

        HomeMenuItem(text = "Inicio", icon = Icons.Filled.Home, onClick = inicioOnClick)
        HomeMenuItem(text = "Dispositivos", icon = Icons.Filled.Bluetooth, onClick = devicesOnClick)
        HomeMenuItem(text = "Tutorial", icon = Icons.Filled.HelpOutline, onClick = { /*TODO*/ })

        HorizontalDivider()

        HomeMenuItem(text = "Ajustes", icon = Icons.Filled.Settings, onClick = { /*TODO*/ })
        HomeMenuItem(text = "Acerca de", icon = Icons.Filled.Info, onClick = { /*TODO*/ })
    }
}

@Composable
fun HomeMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Icon(icon, contentDescription = text)
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = text)
    }
}

@Composable
fun HomeScaffoldContent(bluetoothConnectionManager: BluetoothConnectionManager, drawerState: DrawerState) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { HomeToolBar(drawerState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            HomeContentScreen(
                bluetoothConnectionManager,
                modifier = Modifier.padding(innerPadding)
            )
        }

    }
}

@Composable
fun HomeToolBar(drawerState: DrawerState) {
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
            IconButton(onClick = { /*TODO*/scope.launch { drawerState.open() } }) {
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