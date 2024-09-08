package com.example.handappmobile_epn.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.activity.ComponentActivity


object BluetoothHelper {

    fun getPairedDevices(context: Context, bluetoothAdapter: BluetoothAdapter?):
            Pair<ArrayAdapter<String>, ArrayAdapter<String>>? {
        return try {
            val addressDevices = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)
            val nameDevices = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)

            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                addressDevices.add(device.address)
                nameDevices.add(device.name)
            }

            Pair(addressDevices, nameDevices)
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
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissions.all { perm ->
            ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestBluetoothPermissions(
        activity: ComponentActivity,
        onPermissionsResult: (Boolean) -> Unit
    ) {
        val permissions = arrayOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsResult ->
            val allGranted = permissionsResult.all { it.value }
            onPermissionsResult(allGranted)
        }

        requestPermissionLauncher.launch(permissions)
    }

    fun isBluetoothSupported(bluetoothAdapter: BluetoothAdapter?): Boolean {
        return bluetoothAdapter != null
    }

}