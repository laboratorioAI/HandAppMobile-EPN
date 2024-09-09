package com.example.handappmobile_epn.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme

@Composable
fun ViewContainer(
    bluetoothConnectionManager: BluetoothConnectionManager,
    content: @Composable () -> Unit)
{
    HandAppMobileEPNTheme {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = Color(0xFFFFFFFF) //Blanco
            )
        ) {
            // HomeDrawerScreen(bluetoothConnectionManager)
            content()
        }
    }
}