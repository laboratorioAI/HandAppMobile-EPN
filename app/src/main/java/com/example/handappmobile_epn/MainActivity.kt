package com.example.handappmobile_epn

import SetSystemBars
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
import com.example.handappmobile_epn.ui.components.MenuLateralScreen
import com.example.handappmobile_epn.ui.components.ViewContainer
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme

/**
 * Main activity
 *
 * Autores:
 * - Stiven Moposita
 * - Daniel Lorences
 */
class MainActivity : ComponentActivity() {

    // Adaptador Bluetooth para manejar las conexiones Bluetooth.
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothConnectionManager: BluetoothConnectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el adaptador Bluetooth utilizando el servicio del sistema
        bluetoothAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        bluetoothConnectionManager = BluetoothConnectionManager(bluetoothAdapter)

        // Verifica si el dispositivo soporta Bluetooth
        if (!BluetoothHelper.isBluetoothSupported(bluetoothAdapter)) {
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_LONG).show()
            return // Finaliza la actividad si el dispositivo no soporta Bluetooth
        }

        enableEdgeToEdge()

        setContent {
            // Llamada a la función para configurar la barra de navegación
            SetSystemBars()

            // Variable con permisos necesarios para el uso de Bluetooth
            val permissionsBT = arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )

            // Manejador para solicitar múltiples permisos
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

            // Verifica si los permisos de Bluetooth han sido otorgados
            if (!BluetoothHelper.checkBluetoothPermissions(this)) {
                // Muestra un diálogo de solicitud de permisos si no están concedidos
                AlertDialog(
                    onDismissRequest = {
                        finish() // Finaliza la actividad si el usuario no concede los permisos
                    },
                    title = { Text("Permisos requeridos") },
                    text = { Text("Esta aplicación necesita permisos de Bluetooth para funcionar correctamente.") },
                    confirmButton = {
                        // Botón para conceder los permisos
                        Button(
                            onClick = { requestPermissionLauncher.launch(permissionsBT) },
                            colors = ButtonColors(
                                containerColor = colorResource(id = R.color.app_primary),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text("Conceder permisos")
                        }
                    },
                    dismissButton = {
                        // Botón para salir de la aplicación si no se conceden los permisos
                        Button(
                            onClick = { finish() /*Exit the app*/ },
                            colors = ButtonColors(
                                containerColor = colorResource(id = R.color.app_primary),
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text("Salir")
                        }
                    },
                    containerColor = Color.White
                )
            }

            // Muestra la pantalla principal
            ViewContainer {
                MenuLateralScreen(bluetoothConnectionManager)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val bluetoothConnectionManager = BluetoothConnectionManager(null)
    HandAppMobileEPNTheme {
        MenuLateralScreen(bluetoothConnectionManager)
    }
}
