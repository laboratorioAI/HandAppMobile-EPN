package com.example.handappmobile_epn.ui.screen

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
import com.example.handappmobile_epn.ui.components.HandController
import com.example.handappmobile_epn.ui.components.HomeDrawerScreen
import com.example.handappmobile_epn.ui.components.ViewContainer

@Composable
fun HomeScreen(
    navController: NavController,
    bluetoothConnectionManager: BluetoothConnectionManager
) {
    ViewContainer(bluetoothConnectionManager = bluetoothConnectionManager) {
        HomeDrawerScreen(navController = navController, bluetoothConnectionManager = bluetoothConnectionManager)
    }
}

@Composable
fun HomeContentScreen(
    bluetoothConnectionManager: BluetoothConnectionManager,
    modifier: Modifier = Modifier
) {
    // Accediendo al contexto de la actividad
    val context = LocalContext.current

    // Variables para definir el movimiento de la mano HANDI_EPN
    var lastSliderValuesHandiEPN = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    var codesStringHandiEPN = remember { mutableStateListOf("A", "B", "C", "D", "E", "F") }
    val maxAngleValuesHandiEPN = remember { mutableStateListOf(380.0f, 380.0f, 400.0f, 400.0f, 400.0f, 200.0f) }

    //DEL 0 AL FF
    // Variables para definir el movimiento de la mano FLEXIBLE_V2
    var lastSliderValuesFlexibleV2 = remember { mutableStateListOf(0, 0, 0, 0) }
    var codesStringFlexibleV2 = remember { mutableStateListOf("A", "B", "C", "D") }
    val maxAngleValuesFlexibleV2 = remember { mutableStateListOf(255.0f, 255.0f, 255.0f, 255.0f) }

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
    var estaSeleccionadaFlexibleV2 by remember { mutableStateOf(false) }

    // Declarar el estado para mostrar la pantalla de tutorial
    var mostrarPantallaTutorial by remember { mutableStateOf(false) }

    // Funciones de botones
    val botonTutorial = { mostrarPantallaTutorial = true }
    val botonActivarBluetooth = { /* Acción del botón */
        if (bluetoothConnectionManager.isBluetoothOn()) {
            // variable para cambiar el color
            estaConectadoBluetooth = true
            Toast.makeText(context, "Bluetooth ya se encuentra encendido.", Toast.LENGTH_LONG).show()
        } else {
            estaConectadoBluetooth = false
            val r = bluetoothConnectionManager.bluetoothOn(context)
            // pendiente hacer algo con el resultado
        }
    }
    val botonAbrirMano = {
        sliderValue = 0f // Ajusta el slider al mínimo

        /* Ingresar aquí la funcionalidad del botón */
        bluetoothConnectionManager.sendCommand("O")
    }
    val botonCerrarMano = {
        sliderValue = 100f // Ajusta el slider al máximo

        /* Ingresar aquí la funcionalidad del botón */
        bluetoothConnectionManager.sendCommand("C")
    }
    val botonOK = {
        sliderValue = 0f // Ajusta el slider al mínimo

        /* Ingresar aquí la funcionalidad del botón */
        bluetoothConnectionManager.sendCommand("P")
    }

    // Creación de un bloque vertical de elementos
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Ocupa el ancho disponible completo
                .padding(bottom = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Alinea los elementos en extremos
        ) {

            // Spacer para ajustar el espacio entre el texto y el botón
            Spacer(modifier = Modifier.width(16.dp))

            // Box para centrar el texto
            Box(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio disponible
                    .align(Alignment.CenterVertically) // Centrar verticalmente
            ) {
                Text(
                    text = "HAND APP EPN",
                    modifier = Modifier.align(Alignment.Center) // Centrar el texto horizontalmente
                )
            }

            // Botón circular en la esquina superior derecha
            Button(
                onClick = botonTutorial,
                modifier = Modifier
                    .size(30.dp) // Tamaño
                    .align(Alignment.CenterVertically), // Alinear verticalmente dentro de la fila
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E172F)),
                contentPadding = PaddingValues(0.dp) // Eliminar el padding interno del botón
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = "Ayuda",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize() // Hacer que el ícono llene el botón completo
                )
            }
        }

        // Mostrar la PantallaTutorial cuando se activa
        if (mostrarPantallaTutorial) {
            PantallaTutorial(onDismiss = { mostrarPantallaTutorial = false })
        }

        // Creación de un bloque horizontal de elementos
        Row(verticalAlignment = Alignment.CenterVertically) {
            ///////////////////////////
            /* Botones de bluetooth */
            //////////////////////////
            Button(
                onClick = botonActivarBluetooth,
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
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF022779))
                ) {
                    Text(selectedOption)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val pairedDevices = BluetoothHelper.getPairedDevices(context,
                        bluetoothConnectionManager.getBluetoothAdapter())

                    val addressDevices: ArrayAdapter<String>? = pairedDevices?.first
                    val nameDevices: ArrayAdapter<String>? = pairedDevices?.second

                    nameDevices?.let { array ->
                        for (i in 0 until array.count) {
                            val item = array.getItem(i)
                            DropdownMenuItem(
                                text = {
                                    Text(item.toString())
                                },
                                onClick = {
                                    // Primero guarda la direccion y muestra el nombre como texto
                                    // ----------------------------------------------------------
                                    val auxAddress: String = addressDevices?.getItem(i).toString()

                                    selectedOption = item.toString() // Asigna el nombre del dispositivo seleccionado
                                    expanded = false

                                    // Cambia los booleanos dependiendo del dispositivo seleccionado
                                    if (item.toString() == "STALIN_BT_AUX" || item.toString() == "HANDI_EPN") {
                                        estaSeleccionadaHandiEpn = true
                                        estaSeleccionadaFlexibleV2 = false
                                    } else if (item.toString() == "FLEXIBLE_V2") {
                                        estaSeleccionadaHandiEpn = false
                                        estaSeleccionadaFlexibleV2 = true
                                    }

                                    // Y segundo intenta conectarse con el dispositivo
                                    // ----------------------------------------------------------
                                    bluetoothConnectionManager.connectToDevice(auxAddress)
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val funcionHandiEpn: (String, Boolean) -> Unit = { nombre, estado ->
            var indices = mutableStateListOf<Int>()
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
                "Pulgar Superior" -> {
                    estaPulsadoPulgarSuperior = estado
                    // Para la asignacion del comando
                    if (estaPulsadoPulgarSuperior) indices.add(4)
                    else indices.removeIf { it == 4 }
                }
                "Pulgar Inferior" -> {
                    estaPulsadoPulgarInferior = estado
                    // Para la asignacion del comando
                    if (estaPulsadoPulgarInferior) indices.add(5)
                    else indices.removeIf { it == 5 }
                }
            }
            fingerName = nombre
            sliderValueDisplay = sliderValue
        }

        val funcionFlexibleV2: (String, Boolean) -> Unit = { nombre, estado ->
            var indices = mutableStateListOf<Int>()
            when (nombre) {
                "Meñique", "Anular" -> {
                    estaPulsadoMenique = estado
                    estaPulsadoAnular = estado
                    if (estado && !indices.contains(0)){
                        indices.add(0)  // SOlo agrega el índice una vez
                    }
                    else if (!estado) {
                        indices.removeIf { it == 0 }
                    }
                }
                "Medio" -> {
                    estaPulsadoMedio = estado
                    if (estado) indices.add(1)
                    else indices.removeIf { it == 1 }
                }
                "Índice" -> {
                    estaPulsadoIndice = estado
                    if (estado) indices.add(2)
                    else indices.removeIf { it == 2 }
                }
                "Pulgar Inferior", "Pulgar Superior" -> {
                    estaPulsadoPulgarInferior = estado
                    estaPulsadoPulgarSuperior = estado
                    if (estado && !indices.contains(3)) {
                        indices.add(3) // Solo agrega el índice una vez
                    } else if (!estado) {
                        indices.removeIf { it == 3 }
                    }
                }
            }
            fingerName = nombre
            sliderValueDisplay = sliderValue
        }

        val habilitarMano: Boolean = estaSeleccionadaHandiEpn || estaSeleccionadaFlexibleV2
        var funcionSeleccionada: (String, Boolean) -> Unit = {_, _ -> }
        if (estaSeleccionadaHandiEpn) {
            funcionSeleccionada = funcionHandiEpn
        } else if (estaSeleccionadaFlexibleV2) {
            funcionSeleccionada = funcionFlexibleV2
        }
        ///////////////////
        /* Imagen y dedos */
        ///////////////////
        HandController(
            estaPulsadoPulgarSuperior = estaPulsadoPulgarSuperior,
            estaPulsadoPulgarInferior = estaPulsadoPulgarInferior,
            estaPulsadoIndice = estaPulsadoIndice,
            estaPulsadoMedio = estaPulsadoMedio,
            estaPulsadoAnular = estaPulsadoAnular,
            estaPulsadoMenique = estaPulsadoMenique,
            habilitar = habilitarMano,
            onDedoPulsado = funcionSeleccionada
        )

        ///////////////////
        /* Slider */
        ///////////////////

        // Función que envía los comandos cuando el slider termina de moverse
        var isSliderMoving by remember { mutableStateOf(false) }
        var sliderValue by remember { mutableStateOf(0f) }
        var sliderChangedTimestamp by remember { mutableStateOf(0L) }

        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                sliderChangedTimestamp = System.currentTimeMillis()
                isSliderMoving = true  // Cambia el estado para indicar que el slider se está moviendo
            },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = if (isSliderMoving) Color(0xFFAAAAAA) else Color(0xFF0E172F),  // Cambia el color de la burbuja
                activeTrackColor = if (isSliderMoving) Color(0xFFAAAAAA) else Color(0xFF069606),  // Cambia el color de la parte activa
                inactiveTrackColor = Color.Gray  // Color de la parte no seleccionada
            ),
            onValueChangeFinished = {
                // Referencia a la función sendCommand
                val sendCommand: (String) -> Unit = bluetoothConnectionManager::sendCommand

                // Llamar a la función cuando el slider deja de moverse para HANDI_EPN
                if (estaSeleccionadaHandiEpn){
                    EnviarComandosMovimiento(
                        estadosDedos = listOf(
                            estaPulsadoMenique,
                            estaPulsadoAnular,
                            estaPulsadoMedio,
                            estaPulsadoIndice,
                            estaPulsadoPulgarSuperior,
                            estaPulsadoPulgarInferior
                        ),
                        sliderValue = sliderValue,
                        lastSliderValues = lastSliderValuesHandiEPN,
                        codesString = codesStringHandiEPN,
                        maxAngleValues = maxAngleValuesHandiEPN,
                        sendCommand = sendCommand
                    )
                }

                if (estaSeleccionadaFlexibleV2) {
                    EnviarComandosMovimiento(
                        estadosDedos = listOf(
                            estaPulsadoMenique,
                            estaPulsadoAnular,
                            estaPulsadoMedio,
                            estaPulsadoIndice,
                            estaPulsadoPulgarSuperior,
                            estaPulsadoPulgarInferior
                        ),
                        sliderValue = sliderValue,
                        lastSliderValues = lastSliderValuesFlexibleV2,
                        codesString = codesStringFlexibleV2,
                        maxAngleValues = maxAngleValuesFlexibleV2,  // Mantener los valores como Float
                        sendCommand = sendCommand,
                        useHex = false  // Indicar que se use hexadecimal
                    )
                }
                // Cambia el estado para indicar que el slider dejó de moverse
                isSliderMoving = false
            }
        )
        // Mostrar el valor actual del slider
        Text(
            String.format("%.0f%%", sliderValue),
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
            Button(onClick = botonAbrirMano,
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
            Button(onClick = botonCerrarMano,
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
        Button(onClick = botonOK,
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

/* Función no composable que envía los valores de movimiento en caso de que esté pulsado un dedo */
fun EnviarComandosMovimiento(
    estadosDedos: List<Boolean>,
    sliderValue: Float,
    lastSliderValues: MutableList<Int>,
    codesString: MutableList<String>,
    maxAngleValues: MutableList<Float>,
    sendCommand: (String) -> Unit,
    useHex: Boolean = false // Verificar si se están usando hexadecimales
) {
    for (i in estadosDedos.indices) {
        if (estadosDedos[i]) {
            val valorMovimiento = (maxAngleValues[i] * (sliderValue / 100.0f)).toInt()
            if (valorMovimiento != lastSliderValues[i]) {
                lastSliderValues[i] = valorMovimiento
                // Convertir a hexadecimal si es necesario
                val valorMovimientoStr = if (useHex) {
                    valorMovimiento.toString(16).uppercase().padStart(2, '0') //corregir
                } else {
                    valorMovimiento.toString()
                }
                // Crear el comando a enviar
                val command = codesString[i] + valorMovimientoStr + "\n"
                // Enviar el comando
                sendCommand(command)
            }
        }
    }
}
