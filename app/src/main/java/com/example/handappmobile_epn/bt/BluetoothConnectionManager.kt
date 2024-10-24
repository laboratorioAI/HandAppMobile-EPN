package com.example.handappmobile_epn.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

/**
 * Clase que gestiona las conexiones Bluetooth con dispositivos remotos.
 *
 * @property bluetoothAdapter El adaptador Bluetooth que permite interactuar con dispositivos Bluetooth.
 * @constructor Crea una instancia de `BluetoothConnectionManager`.
 */
class BluetoothConnectionManager(private val bluetoothAdapter: BluetoothAdapter?) {

    private var bluetoothSocket: BluetoothSocket? = null
    private val myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    /**
     * Establece una conexión Bluetooth con un dispositivo remoto utilizando su dirección MAC.
     *
     * @param deviceAddress La dirección MAC del dispositivo Bluetooth al que se desea conectar.
     * @return `true` si la conexión fue exitosa, `false` si hubo algún error.
     */
    fun connectToDevice(deviceAddress: String): Boolean {
        return try {
            // Cancela la búsqueda de dispositivos si está en progreso.
            bluetoothAdapter?.cancelDiscovery()
            // Obtiene el dispositivo remoto usando su dirección MAC.
            val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
            bluetoothSocket = device?.createInsecureRfcommSocketToServiceRecord(myUUID)
            // Conecta con el dispositivo remoto.
            bluetoothSocket?.connect()
            Log.i("BluetoothConnectionManager", "Conexión exitosa")
            true
        } catch (se: SecurityException)
        {
            // Captura errores relacionados con permisos Bluetooth.
            se.printStackTrace()
            Log.e("BluetoothConnectionManager", "Error de seguridad - Permisos Bluetooth no concedidos", se)
            false
        } catch (e: Exception) {
            // Captura cualquier otro error que ocurra durante la conexión.
            e.printStackTrace()
            Log.e("BluetoothConnectionManager", "Error al conectar", e)
            false
        }
    }

    /**
     * Envía un comando al dispositivo conectado a través de Bluetooth.
     *
     * @param command El comando en forma de cadena de texto que se enviará al dispositivo.
     */
    fun sendCommand(command: String) {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(command.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Recibe un mensaje desde el dispositivo conectado a través de Bluetooth.
     *
     * @return El mensaje recibido como una cadena de texto, o una cadena vacía si ocurre un error.
     */
    fun receiveMessage(): String {
        val stringBuilder = StringBuilder()
        if (bluetoothSocket != null) {
            try {
                // Lee datos del flujo de entrada del socket.
                val inputStream = bluetoothSocket!!.inputStream
                var charRead: Int
                while (true) {
                    charRead = inputStream.read()
                    if (charRead == -1) {
                        break // Finaliza si no hay más datos.
                    }
                    val char = charRead.toChar()
                    stringBuilder.append(char)

                    if (char == '\n') {
                        break // Finaliza cuando se encuentra un salto de línea.
                    }
                }
            } catch (e: IOException) {
                // Captura errores durante la recepción de datos.
                e.printStackTrace()
            }
        }
        return stringBuilder.toString().trim()
    }

    /**
     * Verifica si el dispositivo está conectado a través de Bluetooth.
     *
     * @return `true` si hay una conexión activa, `false` de lo contrario.
     */
    fun isConnected(): Boolean {
        return bluetoothSocket?.isConnected ?: false
    }

    /**
     * Obtiene el nombre del dispositivo conectado.
     *
     * @return El nombre del dispositivo si está conectado, o `null` si no lo está.
     */
    fun getNameDeviceConnected(): String? {
        return try {
            if (this.isConnected()) bluetoothSocket?.remoteDevice?.name else null
        } catch (se: SecurityException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Obtiene el adaptador Bluetooth asociado a este administrador de conexión.
     *
     * @return El adaptador Bluetooth, o `null` si no existe.
     */
    fun getBluetoothAdapter(): BluetoothAdapter? {
        return this.bluetoothAdapter
    }

    /**
     * Verifica si el Bluetooth está activado en el dispositivo.
     *
     * @return `true` si el Bluetooth está activado, `false` de lo contrario.
     */
    fun isBluetoothOn(): Boolean {
        return this.bluetoothAdapter?.isEnabled ?: false
    }

}