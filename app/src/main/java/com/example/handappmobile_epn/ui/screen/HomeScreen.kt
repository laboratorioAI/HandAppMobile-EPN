package com.example.handappmobile_epn.ui.screen

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
import com.example.handappmobile_epn.navigation.AppScreens
import com.example.handappmobile_epn.ui.components.HandController
import com.example.handappmobile_epn.ui.components.MenuLateralScreen
import com.example.handappmobile_epn.ui.components.ViewContainer
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme

@Composable
fun HomeScreen(bluetoothConnectionManager: BluetoothConnectionManager) {
    // Accediendo al contexto de la actividad
    val context = LocalContext.current

    // Variables para definir el movimiento de la mano HANDI_EPN
    val lastSliderValuesHandiEPN = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    val codesStringHandiEPN = remember { mutableStateListOf("A", "B", "C", "D", "E", "F") }
    val maxAngleValuesHandiEPN = remember { mutableStateListOf(380.0f, 380.0f, 400.0f, 400.0f, 400.0f, 200.0f) }

    //DEL 0 AL FF
    // Variables para definir el movimiento de la mano FLEXIBLE_V2
    val lastSliderValuesFlexibleV2 = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    val codesStringFlexibleV2 = remember { mutableStateListOf("a", "b", "c", "d", "e", "f") }
    val maxAngleValuesFlexibleV2 = remember { mutableStateListOf(255.0f, 255.0f, 255.0f, 255.0f, 255.0f, 255.0f) }

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
    var estaSeleccionadaHandiEpn by remember { mutableStateOf(true) }
    var estaSeleccionadaFlexibleV2 by remember { mutableStateOf(true) }

    // Declarar el estado para mostrar la pantalla de tutorial
    var mostrarPantallaTutorial by remember { mutableStateOf(false) }


    // Función para actualizar las letras de codesStringFlexibleV2 en función del valor del slider
    val updateCodesStringFlexibleV2: () -> Unit = {
        codesStringFlexibleV2.forEachIndexed { index, _ ->
            codesStringFlexibleV2[index] = if (sliderValue < 0) {
                codesStringFlexibleV2[index].lowercase()
            } else {
                codesStringFlexibleV2[index].uppercase()
            }
        }
    }



    // Funciones de botones
    val botonTutorial = { mostrarPantallaTutorial = true }
    val botonActivarBluetooth = { /* Acción del botón */
        if (bluetoothConnectionManager.isBluetoothOn()) {
            // variable para cambiar el color
            estaConectadoBluetooth = true
            Toast.makeText(context, "Bluetooth ya se encuentra encendido.", Toast.LENGTH_LONG).show()
        } else {
            estaConectadoBluetooth = false
            // val r = bluetoothConnectionManager.bluetoothOn(context)
            // pendiente hacer algo con el resultado
        }
    }
    val botonAbrirMano = {
        sliderValue = 0f // Ajusta el slider al mínimo

        /* Ingresar aquí la funcionalidad del botón */
        if(estaSeleccionadaHandiEpn){
            bluetoothConnectionManager.sendCommand("O")
        }
        if(estaSeleccionadaFlexibleV2){
            bluetoothConnectionManager.sendCommand("O:")
        }

    }
    val botonCerrarMano = {
        sliderValue = 100f // Ajusta el slider al máximo

        /* Ingresar aquí la funcionalidad del botón */
        if(estaSeleccionadaHandiEpn){
            bluetoothConnectionManager.sendCommand("C")
        }
        if(estaSeleccionadaFlexibleV2){
            bluetoothConnectionManager.sendCommand("C:")
        }
    }
    val botonOK = {
        sliderValue = 0f // Ajusta el slider al mínimo


        /* Ingresar aquí la funcionalidad del botón */
        if(estaSeleccionadaHandiEpn){
            bluetoothConnectionManager.sendCommand("P")
        }
        if(estaSeleccionadaFlexibleV2){
            bluetoothConnectionManager.sendCommand("S:")
        }

    }

    // Creación de un bloque vertical de elementos
    Column(
        modifier = Modifier
            .padding(paddingValues = PaddingValues(0.dp, 0.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Caja para imprimir el nombre del dispositivo cuando esté conectado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(colorResource(id = R.color.app_secondary))
                .padding(0.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Dispositivo",
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

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


        Spacer(modifier = Modifier.height(10.dp))



        Box(
            modifier = Modifier
                .height(40.dp)
                .clip(RoundedCornerShape(22.dp))  // Curvatura de las esquinas
                .background(colorResource(id = R.color.app_dark))
                .padding(12.dp, 0.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Palma mano derecha",
                fontSize = 14.sp,
                color = Color.White
            )
        }

        ///////////////////
        /* Slider */
        ///////////////////

        // Función que envía los comandos cuando el slider termina de moverse
        var isSliderMoving by remember { mutableStateOf(false) }
        var sliderChangedTimestamp by remember { mutableStateOf(0L) }

// Cambia el rango y valor inicial del slider según la mano seleccionada
        val sliderValueRange = if (estaSeleccionadaHandiEpn) 0f..100f else -100f..100f

// Ajusta el valor inicial del slider usando remember
        val initialSliderValue = if (estaSeleccionadaHandiEpn) 0f else 0f
        var sliderValue by remember(estaSeleccionadaHandiEpn) { mutableStateOf(initialSliderValue) }

        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                sliderChangedTimestamp = System.currentTimeMillis()
                isSliderMoving = true  // Cambia el estado para indicar que el slider se está moviendo
            },
            valueRange = sliderValueRange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(10.dp, 0.dp)),
            colors = SliderDefaults.colors(
                thumbColor = if (isSliderMoving) colorResource(id = R.color.app_dark)
                else colorResource(id = R.color.app_green),
                activeTrackColor = if (isSliderMoving) colorResource(id = R.color.app_dark)
                else colorResource(id = R.color.app_green),
                inactiveTrackColor = colorResource(id = R.color.app_dark)
            ),
            onValueChangeFinished = {
                // Actualizar las letras en función del valor del slider
                updateCodesStringFlexibleV2()

                val sendCommand: (String) -> Unit = bluetoothConnectionManager::sendCommand

                if (estaSeleccionadaHandiEpn) {
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
                        maxAngleValues = maxAngleValuesFlexibleV2,
                        sendCommand = sendCommand,
                        useHex = true
                    )
                }
                isSliderMoving = false
            }
        )


        // Mostrar el valor actual del slider
        Text(
            String.format("%.0f%%", sliderValue),
        )

        Spacer(modifier = Modifier.height(20.dp))

        ///////////////////////////
        /* Botones abrir, cerrar y OK */
        //////////////////////////

        // Creación de un bloque horizontal de elementos
        Row {
            // Botón para abrir la mano
            Button(onClick = botonAbrirMano,
                modifier = Modifier
                    .size(width = 100.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.app_primary)
                )
            ) {
                Text(text = "Abrir")
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Botón señal "OK"
            Button(onClick = botonOK,
                modifier = Modifier
                    .size(width = 100.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.app_primary)
                )
            ) {
                Text(text = "OK")
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Botón para cerrar la mano
            Button(onClick = botonCerrarMano,
                modifier = Modifier
                    .size(width = 100.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.app_primary)
                )
            ) {
                Text(text = "Cerrar")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))


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
    useHex: Boolean = false
) {
    for (i in estadosDedos.indices) {
        if (estadosDedos[i]) {
            val valorMovimiento = (maxAngleValues[i] * (sliderValue / 100.0f)).toInt()
            if (valorMovimiento != lastSliderValues[i]) {
                lastSliderValues[i] = valorMovimiento
                // Convertir a hexadecimal si es necesario
                val valorMovimientoStr = if (useHex) {
                    valorMovimiento.coerceIn(0, 255) // Limitar el valor a 8 bits (0-255)
                        .toString(16).uppercase().padStart(2, '0') // Convertir a hexadecimal y rellenar con ceros
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