package com.example.handappmobile_epn.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import java.io.IOException
import java.util.UUID

class BluetoothConnectionManager(private val bluetoothAdapter: BluetoothAdapter?) {

    private var bluetoothSocket: BluetoothSocket? = null
    private var deviceAddressConnected: String? = null
    private var isConnected: Boolean = false
    private val myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun connectToDevice(deviceAddress: String): Boolean {
        return try {
            bluetoothAdapter?.cancelDiscovery()
            val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
            bluetoothSocket = device?.createInsecureRfcommSocketToServiceRecord(myUUID)
            bluetoothSocket?.connect()
            deviceAddressConnected = deviceAddress
            isConnected = true
            Log.i("BluetoothConnectionManager", "Conexión exitosa")
            true
        } catch (se: SecurityException)
        {
            se.printStackTrace()
            deviceAddressConnected = null
            isConnected = false
            Log.e("BluetoothConnectionManager", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            false
        } catch (e: Exception) {
            e.printStackTrace()
            deviceAddressConnected = null
            isConnected = false
            Log.e("BluetoothConnectionManager", "Error al conectar", e)
            false
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
            deviceAddressConnected = null
            isConnected = false
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
        return isConnected
    }

    fun getBluetoothAdapter(): BluetoothAdapter? {
        return this.bluetoothAdapter
    }

    fun bluetoothOn(context: Context): Boolean {
        return try {
            if (BluetoothHelper.checkBluetoothPermissions(context)) {
                // Encender Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                context.startActivity(enableBtIntent)
            }
            true
        } catch (se: SecurityException)
        {
            se.printStackTrace()
            isConnected = false
            Log.e("BluetoothConnectionManager", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            false
        } catch (e: Exception) {
            e.printStackTrace()
            isConnected = false
            Log.e("BluetoothConnectionManager", "Error al encender bluetooth", e)
            false
        }
    }

    fun isBluetoothOn(): Boolean {
        return this.bluetoothAdapter?.isEnabled ?: false
    }

}