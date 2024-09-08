package com.example.handappmobile_epn

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
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
            HandAppMobileEPNTheme {
                MaterialTheme(
                    colorScheme = lightColorScheme(
                        background = Color(0xFFFFFFFF) //Blanco
                    )
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        HomeScreen(
                            bluetoothConnectionManager,
                            modifier = Modifier.padding(innerPadding)
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HandAppMobileEPNTheme {
        PantallaTutorial(onDismiss = {})
    }
}