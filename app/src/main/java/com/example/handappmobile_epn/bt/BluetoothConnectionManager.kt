package com.example.handappmobile_epn.bt

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.UUID

class BluetoothConnectionManager(private val bluetoothAdapter: BluetoothAdapter?) {

    private var bluetoothSocket: BluetoothSocket? = null
    private val myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun connectToDevice(deviceAddress: String): Boolean {
        return try {
            bluetoothAdapter?.cancelDiscovery()
            val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
            bluetoothSocket = device?.createInsecureRfcommSocketToServiceRecord(myUUID)
            bluetoothSocket?.connect()
            Log.i("BluetoothConnectionManager", "Conexión exitosa")
            true
        } catch (se: SecurityException)
        {
            se.printStackTrace()
            Log.e("BluetoothConnectionManager", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            false
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BluetoothConnectionManager", "Error al conectar", e)
            false
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendCommand(command: String) {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(command.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected ?: false
    }

    fun getAddressDeviceConnected(): String? {
        return if (this.isConnected()) bluetoothSocket?.remoteDevice?.address else null
    }

    fun getNameDeviceConnected(): String? {
        return try {
            if (this.isConnected()) bluetoothSocket?.remoteDevice?.name else null
        } catch (se: SecurityException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    fun getBluetoothAdapter(): BluetoothAdapter? {
        return this.bluetoothAdapter
    }

    fun isBluetoothOn(): Boolean {
        return this.bluetoothAdapter?.isEnabled ?: false
    }

}