package com.example.handappmobile_epn.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.components.HandController
import java.util.Locale
import kotlin.math.abs

@Composable
fun HomeScreen(bluetoothConnectionManager: BluetoothConnectionManager) {
    // Variables para definir el movimiento de la mano HANDI_EPN
    val lastSliderValuesHandiEPN = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    val codesStringHandiEPN = remember { mutableStateListOf("A", "B", "C", "D", "E", "F") }
    val maxAngleValuesHandiEPN = remember { mutableStateListOf(380.0f, 380.0f, 400.0f, 400.0f, 400.0f, 200.0f) }

    //DEL 0 AL FF
    // Variables para definir el movimiento de la mano FLEXIBLE_V2
    val lastSliderValuesFlexibleV2 = remember { mutableStateListOf(0, 0, 0, 0) }
    val codesStringFlexibleV2 = remember { mutableStateListOf("a", "b", "c", "d") }
    val maxAngleValuesFlexibleV2 = remember { mutableStateListOf(255.0f, 255.0f, 255.0f, 255.0f) }

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

    // Variable para ocultar barra superior si no hay conexion
    var isConnectedToDevice by remember { mutableStateOf(false) }
    isConnectedToDevice = bluetoothConnectionManager.isConnected()

    // Variable para cambiar el texto del boton OK y STOP
    var btnOkStop by remember { mutableStateOf("OK") }

    // Variable para ctualizar el nombre del dispositivo conectado
    var nameDeviceConnected by remember { mutableStateOf("Dispositivo") }
    if (isConnectedToDevice) {
        nameDeviceConnected = bluetoothConnectionManager.getNameDeviceConnected() ?: "Dispositivo"

        // Se activan funcionalidades especificas de las manos disponibles
        if (nameDeviceConnected == "HANDI_EPN") {
            estaSeleccionadaHandiEpn = true
        } else if (nameDeviceConnected == "Prosthesis_EPN_v2") {
            estaSeleccionadaFlexibleV2 = true
            btnOkStop = "Stop"
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
                .background(if (isConnectedToDevice) colorResource(id = R.color.app_secondary) else Color.Transparent)
                .padding(0.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameDeviceConnected,
                color = if (isConnectedToDevice) Color.Black else Color.Transparent,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val funcionHandiEpn: (String, Boolean) -> Unit = { nombre, estado ->
            when (nombre) {
                "Meñique" -> {
                    estaPulsadoMenique = estado
                }
                "Anular" -> {
                    estaPulsadoAnular = estado
                }
                "Medio" -> {
                    estaPulsadoMedio = estado
                }
                "Índice" -> {
                    estaPulsadoIndice = estado
                }
                "Pulgar Superior" -> {
                    estaPulsadoPulgarSuperior = estado
                }
                "Pulgar Inferior" -> {
                    estaPulsadoPulgarInferior = estado
                }
            }
        }

        val funcionFlexibleV2: (String, Boolean) -> Unit = { nombre, estado ->
            when (nombre) {
                "Meñique", "Anular" -> {
                    estaPulsadoMenique = estado
                    estaPulsadoAnular = estado
                }
                "Medio" -> {
                    estaPulsadoMedio = estado
                }
                "Índice" -> {
                    estaPulsadoIndice = estado
                }
                "Pulgar Inferior", "Pulgar Superior" -> {
                    estaPulsadoPulgarInferior = estado
                    estaPulsadoPulgarSuperior = estado
                }
            }
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

        // Cambia el rango y valor inicial del slider según la mano seleccionada
        val sliderValueRange = if (estaSeleccionadaHandiEpn) 0f..100f else -100f..100f

        // Ajusta el valor inicial del slider usando remember
        val initialSliderValue = 0f
        var sliderValue by remember(estaSeleccionadaHandiEpn) { mutableFloatStateOf(initialSliderValue) }

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

        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
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
                    enviarComandosMovimiento(
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
                    enviarComandosMovimiento(
                        estadosDedos = listOf(
                            estaPulsadoMenique, // Cuenta como meñique y anular
                            estaPulsadoMedio,
                            estaPulsadoIndice,
                            estaPulsadoPulgarSuperior // Cuenta como pulgar superior e inferior
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
            String.format(Locale.US, "%.0f%%", sliderValue),
        )

        Spacer(modifier = Modifier.height(20.dp))

        ///////////////////////////
        /* Botones abrir, cerrar y OK */
        //////////////////////////

        // Funciones lambda para los botones

        // Funciones de botones
        val botonAbrirMano = {
            /* Ingresar aquí la funcionalidad del botón */
            if(estaSeleccionadaHandiEpn){
                bluetoothConnectionManager.sendCommand("O")
                sliderValue = 0f // Ajusta el slider al mínimo
            }
            if(estaSeleccionadaFlexibleV2){
                bluetoothConnectionManager.sendCommand("O:")
                sliderValue = -100f // Ajusta el slider al mínimo
            }

        }
        val botonCerrarMano = {
            /* Ingresar aquí la funcionalidad del botón */
            if(estaSeleccionadaHandiEpn){
                bluetoothConnectionManager.sendCommand("C")
                sliderValue = 100f // Ajusta el slider al máximo
            }
            if(estaSeleccionadaFlexibleV2){
                bluetoothConnectionManager.sendCommand("C:")
                sliderValue = 100f // Ajusta el slider al máximo
            }
        }
        val botonOK = {
            /* Ingresar aquí la funcionalidad del botón */
            if(estaSeleccionadaHandiEpn){
                bluetoothConnectionManager.sendCommand("P")
                sliderValue = 0f // Ajusta el slider al mínimo
            }
            if(estaSeleccionadaFlexibleV2){
                bluetoothConnectionManager.sendCommand("S:")
                sliderValue = 0f // Ajusta el slider al mínimo
            }

        }

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

            // Botón señal "OK" y "Stop" (Depende del dispositivo)
            Button(onClick = botonOK,
                modifier = Modifier
                    .size(width = 100.dp, 40.dp),

                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.app_primary)
                )
            ) {
                Text(text = btnOkStop)
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
fun enviarComandosMovimiento(
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
            var valorMovimiento = (maxAngleValues[i] * (sliderValue / 100.0f)).toInt()
            if (valorMovimiento != lastSliderValues[i]) {
                lastSliderValues[i] = valorMovimiento
                // Tomar solo el valor absoluto del valorMovimiento ya que la direccion (- +) se determina con los codigos A, B, C y D (en el caso de flexible v2)
                valorMovimiento = abs(valorMovimiento)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(BluetoothConnectionManager(null))
}