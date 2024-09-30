package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.screen.HomeScreen

@Composable
fun HandController(
    estaPulsadoPulgarSuperior: Boolean,
    estaPulsadoPulgarInferior: Boolean,
    estaPulsadoIndice: Boolean,
    estaPulsadoMedio: Boolean,
    estaPulsadoAnular: Boolean,
    estaPulsadoMenique: Boolean,
    habilitar: Boolean,
    onDedoPulsado: (String, Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            // Imagen de la mano
            Image(
                painter = painterResource(id = R.drawable.mano),
                contentDescription = "Mano en blanco y negro",
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Red),
                contentScale = ContentScale.FillHeight
            )

            // Imágenes condicionales de los dedos
            FingerImages(
                estaPulsadoPulgarSuperior,
                estaPulsadoPulgarInferior,
                estaPulsadoIndice,
                estaPulsadoMedio,
                estaPulsadoAnular,
                estaPulsadoMenique
            )

            // Botones de los dedos
            if (habilitar) {
                HandButtons(onDedoPulsado)
            }
        }
    }
}

@Composable
fun FingerImages(
    estaPulsadoPulgarSuperior: Boolean,
    estaPulsadoPulgarInferior: Boolean,
    estaPulsadoIndice: Boolean,
    estaPulsadoMedio: Boolean,
    estaPulsadoAnular: Boolean,
    estaPulsadoMenique: Boolean
) {
    MostrarImagen(estaPulsadoPulgarSuperior, R.drawable.pulgarsuperiorverde, 269.dp, 175.dp, 103.dp, 103.dp)
    MostrarImagen(estaPulsadoPulgarInferior, R.drawable.pulgarinferiorverde, 196.dp, 247.dp, 120.dp, 120.dp)
    MostrarImagen(estaPulsadoIndice, R.drawable.indiceverde, 133.dp, 17.dp, 245.dp, 245.dp)
    MostrarImagen(estaPulsadoMedio, R.drawable.medioverde, 55.dp, (-11).dp, 258.dp, 258.dp)
    MostrarImagen(estaPulsadoAnular, R.drawable.anularverde, 11.dp, 25.dp, 215.dp, 215.dp)
    MostrarImagen(estaPulsadoMenique, R.drawable.meniqueverde, (-12).dp, 89.dp, 188.dp, 188.dp)
}

@Composable
fun HandButtons(onDedoPulsado: (String, Boolean) -> Unit) {
    LogicaBotonesMano(
        onDedoPulsado = onDedoPulsado
    )
}

/* Creación de los botones de la mano */
@Composable
fun LogicaBotonesMano(onDedoPulsado: (String, Boolean) -> Unit) {
    // Botones y lógica de HandiEpn aquí
    BotonDedo("Pulgar Superior", 270.dp, 210.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Pulgar Inferior", 210.dp, 280.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Índice", 140.dp, 120.dp, -72f, 210.dp, 40.dp, onDedoPulsado)
    BotonDedo("Medio", 60.dp, 90.dp, 90f, 230.dp, 35.dp, onDedoPulsado)
    BotonDedo("Anular", 10.dp, 90.dp, 75f, 200.dp, 35.dp, onDedoPulsado)
    BotonDedo("Meñique", (-20).dp, 140.dp, 55f, 155.dp, 35.dp, onDedoPulsado)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(BluetoothConnectionManager(null))
}