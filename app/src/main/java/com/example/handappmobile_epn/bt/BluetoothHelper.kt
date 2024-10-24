package com.example.handappmobile_epn.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

object BluetoothHelper {

    /**
     * Obtiene una lista de dispositivos emparejados con el adaptador Bluetooth proporcionado.
     *
     * @param context El contexto de la aplicación.
     * @param bluetoothAdapter El adaptador Bluetooth del dispositivo, que se usará para obtener los dispositivos emparejados.
     * @return Un mapa que contiene los nombres de los dispositivos emparejados y sus direcciones MAC, o `null` en caso de error.
     */
    fun getPairedDevices(context: Context, bluetoothAdapter: BluetoothAdapter?):
            Map<String, String>? {
        return try {
            // Mapa que almacenará los nombres y direcciones de los dispositivos emparejados.
            val pairedDevicesMap = mutableMapOf<String, String>()

            // Obtiene los dispositivos emparejados.
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            // Agrega los dispositivos emparejados al mapa.
            pairedDevices?.forEach { device ->
                pairedDevicesMap[device.name] = device.address
            }

            pairedDevicesMap
        } catch (se: SecurityException) {
            // Captura errores relacionados con permisos Bluetooth.
            se.printStackTrace()
            Log.e("BluetoothHelper", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            null
        } catch (e: Exception) {
            // Captura cualquier otro error
            e.printStackTrace()
            Log.e("BluetoothHelper", "Error al obtener dispositivos emparejados", e)
            null
        }
    }

    /**
     * Verifica si la aplicación tiene los permisos necesarios para usar Bluetooth.
     *
     * @param context El contexto de la aplicación, necesario para verificar los permisos.
     * @return `true` si todos los permisos requeridos para el uso de Bluetooth están concedidos, `false` de lo contrario.
     */
    fun checkBluetoothPermissions(context: Context): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN
        )

        // Verifica si todos los permisos del array han sido concedidos.
        return permissions.all { perm ->
            ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Verifica si el dispositivo admite Bluetooth.
     *
     * @param bluetoothAdapter El adaptador Bluetooth del dispositivo.
     * @return `true` si el dispositivo admite Bluetooth, `false` si el adaptador Bluetooth es `null`.
     */
    fun isBluetoothSupported(bluetoothAdapter: BluetoothAdapter?): Boolean {
        return bluetoothAdapter != null
    }

}