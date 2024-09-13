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
            android.Manifest.permission.BLUETOOTH_SCAN
        )
        return permissions.all { perm ->
            ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun isBluetoothSupported(bluetoothAdapter: BluetoothAdapter?): Boolean {
        return bluetoothAdapter != null
    }

    @Composable
    fun BluetoothOffComposable(
        onResult: (Boolean) -> Unit)
    {
        val disableBtIntent = Intent("android.bluetooth.adapter.action.REQUEST_DISABLE")
        val bluetoothOffLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onResult(result.resultCode == Activity.RESULT_OK)
        }
        bluetoothOffLauncher.launch(disableBtIntent)
    }

}