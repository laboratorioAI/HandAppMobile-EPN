package com.example.handappmobile_epn

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
import com.example.handappmobile_epn.navigation.AppNavigation
import com.example.handappmobile_epn.ui.components.MenuLateralScreen
import com.example.handappmobile_epn.ui.components.ViewContainer
import com.example.handappmobile_epn.ui.screen.DevicesScreen
import com.example.handappmobile_epn.ui.screen.HomeScreen
import com.example.handappmobile_epn.ui.screen.PantallaTutorial
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothConnectionManager: BluetoothConnectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializacion de bluetooth adapter
        bluetoothAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        bluetoothConnectionManager = BluetoothConnectionManager(bluetoothAdapter)

        if (!BluetoothHelper.isBluetoothSupported(bluetoothAdapter)) {
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_LONG).show()
            return
        }

        enableEdgeToEdge()
        setContent {
            ViewContainer() {
                MenuLateralScreen(bluetoothConnectionManager)
            }
        }
    }
}

// @Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HandAppMobileEPNTheme {
        PantallaTutorial(onDismiss = {})
    }
}