package com.example.handappmobile_epn

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme
import java.io.IOException
import java.util.UUID

// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
const val REQUEST_ENABLE_BT = 1
// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

class MainActivity : ComponentActivity() {

    // bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
    lateinit var mBtAdapter: BluetoothAdapter
    var mAddressDevices: ArrayAdapter<String>? = null
    var mNameDevices: ArrayAdapter<String>? = null

    companion object {
        public var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        public var m_bluetoothSocket: BluetoothSocket? = null

        public var m_isConnected: Boolean = false
        public lateinit var m_address: String
    }
    // bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cccccccccccccccccccccccccccccccccccccccccccccccc
        mAddressDevices = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        mNameDevices = ArrayAdapter(this, android.R.layout.simple_list_item_1)

        val someActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == REQUEST_ENABLE_BT) {
                Log.i("MainActivity", "ACTIVIDAD REGISTRADA")
            }
        }

        // Inicializacion de bluetooth adapter
        mBtAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

        // Verificar si el adaptador Bluetooth está disponible
        if (mBtAdapter == null) {
            // El dispositivo no soporta Bluetooth
            Toast.makeText(this, "Bluetooth no soportado", Toast.LENGTH_LONG).show()
        }
        else {
            // Toast.makeText(this, "Bluetooth soportado correctamente", Toast.LENGTH_LONG).show()
        }
        // cccccccccccccccccccccccccccccccccccccccccccccccc

        enableEdgeToEdge()
        setContent {
            HandAppMobileEPNTheme {
                MaterialTheme(
                    colorScheme = lightColorScheme(
                        background = Color(0xFFFFFFFF) //Blanco
                    )
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        PantallaPrincipal(
                            mBtAdapter,
                            mAddressDevices,
                            mNameDevices,
                            modifier = Modifier.padding(innerPadding)
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(
    mBtAdapter: BluetoothAdapter,
    mAddressDevices: ArrayAdapter<String>?,
    mNameDevices: ArrayAdapter<String>?,
    modifier: Modifier = Modifier
) {

    // dddddddddddddddddddddddddddddddddddddddddddddddd
    // Accediendo al contexto de la actividad
    val context = LocalContext.current

    // Crear un launcher para solicitar permisos
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permiso concedido, encender Bluetooth
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivity(enableBtIntent)
        } else {
            // Permiso no concedido
            Toast.makeText(context, "No tiene los permisos de conexión Bluetooth.", Toast.LENGTH_LONG).show()
        }
    }
    6
    // Verificar si se cuenta con los permisos de bluetooth
    val isPermissionGranted = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.BLUETOOTH_CONNECT
    ) == PackageManager.PERMISSION_GRANTED

    var lastSliderValues = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    var codesString = remember { mutableStateListOf("A", "B", "C", "D", "E", "F") }
    val maxAngleValues = remember { mutableStateListOf(380.0f, 380.0f, 400.0f, 400.0f, 400.0f, 200.0f) }
    // dddddddddddddddddddddddddddddddddddddddddddddddd

    // booleano para comprobar si el bluetooth está conectado
    var estaConectadoBluetooth by remember { mutableStateOf(false) }

    // Estado para manejar el Dropdown
    var expanded by remember { mutableStateOf(false) }

    // Estado para controlar el diálogo y el nombre del dedo
    var fingerName by remember { mutableStateOf("") }

    // Valor del slider
    var sliderValue by remember { mutableStateOf(0f) }

    // Estado para mostrar el valor del slider cuando se presiona un botón de dedo
    var sliderValueDisplay by remember { mutableStateOf(0f) }

    // Estado para implementar el nombre del dispositivo bluetooth seleccionado  cambiarlo en el botón
    var selectedOption by remember { mutableStateOf("Vincular Bluetooth") }

    // Comprobar si los dedos están o no pulsados
    var estaPulsadoPulgarSuperior by remember { mutableStateOf(false) }
    var estaPulsadoPulgarInferior by remember { mutableStateOf(false) }
    var estaPulsadoIndice by remember { mutableStateOf(false) }
    var estaPulsadoMedio by remember { mutableStateOf(false) }
    var estaPulsadoAnular by remember { mutableStateOf(false) }
    var estaPulsadoMenique by remember { mutableStateOf(false) }

    // Comprobar la mano seleccionada para moverse
    var estaSeleccionadaHandiEpn by remember { mutableStateOf(false) }
    var estaSeleccionadaFlexibleB2 by remember { mutableStateOf(false) }

    // Creación de un bloque vertical de elementos
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HANDI EPN",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Creación de un bloque horizontal de elementos
        Row(verticalAlignment = Alignment.CenterVertically) {
            ///////////////////////////
            /* Botones de bluetooth */
            //////////////////////////
            Button(
                onClick = { /* Acción del botón */
                    if (mBtAdapter.isEnabled) {
                        // variable para cambiar el color
                        estaConectadoBluetooth = true
                        Toast.makeText(context, "Bluetooth ya se encuentra encendido.", Toast.LENGTH_LONG).show()
                    } else {
                        estaConectadoBluetooth = false
                        if (isPermissionGranted) {
                            // Permiso concedido, encender Bluetooth
                            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            context.startActivity(enableBtIntent)
                        } else {
                            // Solicitar permiso si no está concedido
                            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (estaConectadoBluetooth) Color(0xFF069606) else Color(
                        0xFFAA0A32
                    )
                )
            ) {
                Text(text = "Activar Bluetooth")
            }

            Spacer(modifier = Modifier.width(20.dp))

            // DropdownMenu para elegir conexión Bluetooth
            Box (modifier = Modifier.width(150.dp)){

                TextButton(
                    onClick = {

                        expanded = true

                        if (mBtAdapter.isEnabled) {
                            val pairedDevices: Set<BluetoothDevice>? = mBtAdapter?.bondedDevices
                            mAddressDevices!!.clear()
                            mNameDevices!!.clear()

                            pairedDevices?.forEach { device ->
                                val deviceName = device.name
                                val deviceHardwareAddress = device.address // MAC address
                                mAddressDevices!!.add(deviceHardwareAddress)
                                // ............ EN ESTE PUNTO GUARDO LOS NOMBRES A MOSTRARSE EN EL COMBO BOX
                                mNameDevices!!.add(deviceName)
                            }
                        } else {
                            val noDevices = "Ningun dispositivo pudo ser emparejado"
                            mAddressDevices!!.add(noDevices)
                            mNameDevices!!.add(noDevices)
                            Toast.makeText(context, "Primero active bluetooth.", Toast.LENGTH_LONG).show()
                        }

                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF022779))
                ) {
                    Text(selectedOption)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val devices = listOf("HANDI_EPN", "FLEXIBLE_B2") // Lista de dispositivos

                    mNameDevices?.let { array ->
                        for (i in 0 until array.count) {
                            val item = array.getItem(i)
                            DropdownMenuItem(
                                text = {
                                    Text(item.toString())
                                },
                                onClick = {
                                    // Primero guarda la direccion y muestra el nombre como texto
                                    // ----------------------------------------------------------
                                    MainActivity.m_address = mAddressDevices?.getItem(i).toString()

                                    selectedOption = item.toString() // Asigna el nombre del dispositivo seleccionado
                                    expanded = false

                                    // Cambia los booleanos dependiendo del dispositivo seleccionado
                                    if (item.toString() == "STALIN_BT_AUX" || item.toString() == "HANDI_EPN") {
                                        estaSeleccionadaHandiEpn = true
                                        estaSeleccionadaFlexibleB2 = false
                                    } else if (item.toString() == "FLEXIBLE_B2") {
                                        estaSeleccionadaHandiEpn = false
                                        estaSeleccionadaFlexibleB2 = true
                                    }

                                    // Y segundo intenta conectarse con el dispositivo
                                    // ----------------------------------------------------------
                                    try {
                                        if (MainActivity.m_bluetoothSocket == null || !MainActivity.m_isConnected) {
                                            // Toast.makeText(context, MainActivity.m_address, Toast.LENGTH_LONG).show()
                                            // Cancel discovery because it otherwise slows down the connection.
                                            mBtAdapter?.cancelDiscovery()
                                            val device: BluetoothDevice = mBtAdapter.getRemoteDevice(MainActivity.m_address)
                                            MainActivity.m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(MainActivity.m_myUUID)
                                            MainActivity.m_bluetoothSocket!!.connect()
                                        }

                                        Toast.makeText(context, "CONEXION EXITOSA", Toast.LENGTH_LONG).show()
                                        Log.i("MainActivity", "CONEXION EXITOSA")
                                    } catch (e: IOException) {
                                        // connectSuccess = false
                                        e.printStackTrace()
                                        Toast.makeText(context, "ERROR DE CONEXION", Toast.LENGTH_LONG).show()
                                        Log.i("MainActivity", "ERROR DE CONEXION")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Funcion para enviar comandos a microcontrolador
        fun sendCommand(input: String) {
            if (MainActivity.m_bluetoothSocket != null) {
                try {
                    MainActivity.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                    // Toast.makeText(context, "Comando enviado exitosamente.", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        ///////////////////
        /* Imagen y dedos */
        ///////////////////
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Altura fija para evitar que empuje otros elementos
        ) {

            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.mano),
                contentDescription = "Mano en blanco y negro",
                modifier = Modifier.fillMaxSize()
            )

            // Mostrar las imágenes de los dedos si los estados son true
            MostrarImagen(estaPulsadoPulgarSuperior, R.drawable.pulgarsuperiorverde, 248.dp, 175.dp, 100.dp, 100.dp)
            MostrarImagen(estaPulsadoPulgarInferior, R.drawable.pulgarinferiorverde, 183.dp, 245.dp, 110.dp, 110.dp)
            MostrarImagen(estaPulsadoIndice, R.drawable.indiceverde, 120.dp, 28.dp, 235.dp, 235.dp)
            MostrarImagen(estaPulsadoMedio, R.drawable.medioverde, 49.dp, 3.dp, 245.dp, 245.dp)
            MostrarImagen(estaPulsadoAnular, R.drawable.anularverde, 8.dp, 36.dp, 205.dp, 205.dp)
            MostrarImagen(estaPulsadoMenique, R.drawable.meniqueverde, -14.dp, 95.dp, 180.dp, 180.dp)

            // Botones de los dedos según la mano seleccionada
            if (estaSeleccionadaHandiEpn) {
                var indices = remember { mutableStateListOf<Int>() }
                LogicaBotonesMano(
                    sliderValue,
                    onDedoPulsado = { nombre, estado ->

                        when (nombre) {
                            "Meñique" -> {
                                estaPulsadoMenique = estado
                                // Para la asignacion del comando string en las siguientes lineas
                                if (estaPulsadoMenique) indices.add(0)
                                else indices.removeIf { it == 0 }
                            }
                            "Anular" -> {
                                estaPulsadoAnular = estado
                                // Para la asignacion del comando
                                if (estaPulsadoAnular) indices.add(1)
                                else indices.removeIf { it == 1 }
                            }
                            "Medio" -> {
                                estaPulsadoMedio = estado
                                // Para la asignacion del comando
                                if (estaPulsadoMedio) indices.add(2)
                                else indices.removeIf { it == 2 }
                            }
                            "Índice" -> {
                                estaPulsadoIndice = estado
                                // Para la asignacion del comando
                                if (estaPulsadoIndice) indices.add(3)
                                else indices.removeIf { it == 3 }
                            }
                            "Pulgar Inferior" -> {
                                estaPulsadoPulgarInferior = estado
                                // Para la asignacion del comando
                                if (estaPulsadoPulgarInferior) indices.add(4)
                                else indices.removeIf { it == 4 }
                            }
                            "Pulgar Superior" -> {
                                estaPulsadoPulgarSuperior = estado
                                // Para la asignacion del comando
                                if (estaPulsadoPulgarSuperior) indices.add(5)
                                else indices.removeIf { it == 5 }
                            }
                        }
                        fingerName = nombre
                        sliderValueDisplay = sliderValue

                        // Si se ha activado el estado entonces se envia el comando
                        if (estado) {
                            indices.forEach { i ->
                                // Se envia el comando por bluetooth
                                var valorMovimiento = (maxAngleValues.get(i) * (sliderValue/100.0f)).toInt()
                                if (valorMovimiento != lastSliderValues.get(i)) {
                                    lastSliderValues.set(i, valorMovimiento)
                                    val command = codesString.get(i).toString() + valorMovimiento.toString() + "\n"
                                    sendCommand(command)
                                }
                            }
                        }
                    }
                )
            } else if (estaSeleccionadaFlexibleB2) {
                LogicaBotonesMano(
                    sliderValue,
                    onDedoPulsado = { nombre, estado ->
                        when (nombre) {
                            "Pulgar Superior", "Pulgar Inferior" -> {
                                estaPulsadoPulgarSuperior = estado
                                estaPulsadoPulgarInferior = estado
                            }

                            "Anular" -> {
                                estaPulsadoAnular = estado
                                estaPulsadoMenique = estado
                            }

                            "Meñique" -> {
                                estaPulsadoMenique = estado
                                estaPulsadoAnular = estado
                            }

                            else -> {
                                when (nombre) {
                                    "Índice" -> estaPulsadoIndice = estado
                                    "Medio" -> estaPulsadoMedio = estado
                                }
                            }
                        }
                        fingerName = nombre
                        sliderValueDisplay = sliderValue
                    }
                )
            }
        }

        ///////////////////
        /* Slider */
        ///////////////////
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF0E172F),         // Color del círculo del deslizador
                activeTrackColor = Color(0xFF069606),   // Color de la barra activa
                inactiveTrackColor = Color.Gray              // Color de la barra inactiva
            )
        )
        // Mostrar el valor actual del slider
        Text(
            String.format("%.2f", sliderValue),
        )

        // Mostrar el valor del slider cuando se presiona un botón de dedo
        Text(
            text = "Valor del slider al presionar el dedo: ${String.format("%.2f", sliderValueDisplay)}"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ///////////////////////////
        /* Botones abrir, cerrar y OK */
        //////////////////////////

        // Creación de un bloque horizontal de elementos
        Row {
            // Botón para abrir la mano
            Button(onClick = {
                sliderValue = 100f // Ajusta el slider al máximo

                /* Ingresar aquí la funcionalidad del botón */
                sendCommand("O")
            },
                modifier = Modifier
                    .size(width = 140.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =  Color(0xFF0E172F)
                )
            ) {
                Text(text = "Abrir Mano")
            }

            Spacer(modifier = Modifier.width(30.dp))

            // Botón para cerrar la mano
            Button(onClick = {
                sliderValue = 0f // Ajusta el slider al mínimo

                /* Ingresar aquí la funcionalidad del botón */
                sendCommand("C")
            },
                modifier = Modifier
                    .size(width = 140.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =  Color(0xFF0E172F)
                )
            ) {
                Text(text = "Cerrar Mano")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón señal "OK"
        Button(onClick = {
            sliderValue = 0f // Ajusta el slider al mínimo

            /* Ingresar aquí la funcionalidad del botón */
            sendCommand("P")
        },
            modifier = Modifier
                .size(width = 310.dp, 40.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFF0E172F)
            )
        ) {
            Text(text = "OK")
        }
    }
}

/* Creación de los botones de la mano*/
@Composable
fun LogicaBotonesMano(sliderValue: Float, onDedoPulsado: (String, Boolean) -> Unit) {
    // Botones y lógica de HandiEpn aquí
    BotonDedo("Pulgar Superior", 260.dp, 200.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Pulgar Inferior", 200.dp, 270.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Índice", 130.dp, 120.dp, -70f, 200.dp, 35.dp, onDedoPulsado)
    BotonDedo("Medio", 50.dp, 90.dp, 90f, 230.dp, 35.dp, onDedoPulsado)
    BotonDedo("Anular", 0.dp, 90.dp, 75f, 200.dp, 35.dp, onDedoPulsado)
    BotonDedo("Meñique", (-30).dp, 140.dp, 55f, 160.dp, 35.dp, onDedoPulsado)
}

/* Función para declarar los datos de los botones de los dedos */
@Composable
fun BotonDedo(
    nombre: String,
    offsetX: Dp,
    offsetY: Dp,
    rotacion: Float,
    width: Dp,
    height: Dp,
    onClick: (String, Boolean) -> Unit
) {
    var estado by remember { mutableStateOf(false) } // Estado inicial del botón

    Button(
        onClick = {
            estado = !estado  // Alterna el estado entre true y false
            onClick(nombre, estado)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // Fondo transparente para hacer invisible el botón
            contentColor = Color.Transparent // Texto transparente para mantener el botón invisible
        ),
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .rotate(rotacion)
            .size(width = width, height = height)
            .border(0.dp, Color.Transparent) // Eliminar borde si es necesario
    ) {
        /* El botón es invisible, pero realiza la acción de alternar el estado */
    }
}

/* Función que muestra la imagen según el estado */
@Composable
fun MostrarImagen(mostrar: Boolean, imagenRes: Int, offsetX: Dp, offsetY: Dp, width: Dp, height: Dp) {
    if (mostrar) {
        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = null,
            modifier = Modifier
                .offset(x = offsetX, y = offsetY)
                .size(width = width, height = height)
        )
    }
}

/* Función para implementar la lógica del movimiento de la mano HANDI_EPN */
@Composable
fun ControlarDedosHandiEpn(){

}

/* Función para implementar la lógica del movimiento de la mano Flexible_B2 */
@Composable
fun ControlarDedosFlexibleB2(){

}