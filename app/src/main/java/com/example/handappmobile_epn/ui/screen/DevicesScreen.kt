package com.example.handappmobile_epn.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.navigation.AppScreens
import com.example.handappmobile_epn.ui.components.BackScaffoldContent
import com.example.handappmobile_epn.ui.components.HomeDrawerScreen
import com.example.handappmobile_epn.ui.components.ViewContainer

@Composable
fun DevicesScreen(
    navController: NavController,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
//    ViewContainer(bluetoothConnectionManager = bluetoothConnectionManager) {
//        ScaffoldDevicesScreen(navController = navController, bluetoothConnectionManager = bluetoothConnectionManager)
//    }


    // ScaffoldDevicesScreen(navController = navController, bluetoothConnectionManager = bluetoothConnectionManager)

    BackScaffoldContent() { innerPadding ->
        ContentDevicesScreen(
            navController = navController,
            bluetoothConnectionManager = bluetoothConnectionManager
        )
    }
}

@Composable
fun ScaffoldDevicesScreen(
    navController: NavController,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
    BackScaffoldContent() { innerPadding ->
        ContentDevicesScreen(
            navController = navController,
            bluetoothConnectionManager = bluetoothConnectionManager
        )
    }
}

@Composable
fun ContentDevicesScreen(
    navController: NavController,
    bluetoothConnectionManager: BluetoothConnectionManager)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla de dispositivos BT")
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver")
        }
    }
}