package com.example.handappmobile_epn.bt

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable


object BluetoothHelper {

    fun getPairedDevices(context: Context, bluetoothAdapter: BluetoothAdapter?):
            Map<String, String>? {
        return try {
            val pairedDevicesMap = mutableMapOf<String, String>()

            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                pairedDevicesMap[device.name] = device.address
            }

            pairedDevicesMap
        } catch (se: SecurityException) {
            se.printStackTrace()
            Log.e("BluetoothHelper", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BluetoothHelper", "Error al obtener dispositivos emparejados", e)
            null
        }
    }

    fun checkBluetoothPermissions(context: Context): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN
        )
        return permissions.all { perm ->
            ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun isBluetoothSupported(bluetoothAdapter: BluetoothAdapter?): Boolean {
        return bluetoothAdapter != null
    }

}