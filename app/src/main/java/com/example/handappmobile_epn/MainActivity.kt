package com.example.handappmobile_epn

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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

        // Bluetooth adapter initialization - allows interact with bluetooth hardware
        bluetoothAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        bluetoothConnectionManager = BluetoothConnectionManager(bluetoothAdapter)

        if (!BluetoothHelper.isBluetoothSupported(bluetoothAdapter)) {
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_LONG).show()
            return
        }

        enableEdgeToEdge()
        setContent {
            // Request Bluetooth permissions
            val permissionsBT = arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )

            val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
                results.forEach { (permission, isGranted) ->
                    recreate() // Recreate the activity to apply the new permissions

                    if (isGranted) {
                        Log.d("MessageBTRequest", "$permission - Permission granted")
                    } else {
                        Log.d("MessageBTRequest", "$permission - Permission denied")
                    }
                }
            }

            Log.d("MensajeAA", "Hola mundo")
            if(!BluetoothHelper.checkBluetoothPermissions(this))
            {
                AlertDialog(
                    onDismissRequest = {
                        finish() // Exit the app
                    },
                    title = { Text("Permisos requeridos") },text = { Text("Esta aplicación necesita permisos de Bluetooth para funcionar correctamente.") },
                    confirmButton = {
                        Button(
                            onClick = { requestPermissionLauncher.launch(permissionsBT) },
                            colors = ButtonColors(
                                containerColor = colorResource(id = R.color.app_primary),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White)
                        )
                        {
                            Text("Conceder permisos")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { finish() /*Exit the app*/ },
                            colors = ButtonColors(
                                containerColor = colorResource(id = R.color.app_primary),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White))
                        {
                            Text("Salir")
                        }
                    },
                    containerColor = Color.White
                )
            }

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