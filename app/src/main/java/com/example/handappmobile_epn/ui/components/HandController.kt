package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.handappmobile_epn.R

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
            .height(400.dp)
    ) {
        // Imagen de la mano
        Image(
            painter = painterResource(id = R.drawable.mano),
            contentDescription = "Mano en blanco y negro",
            modifier = Modifier.fillMaxSize()
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

@Composable
fun FingerImages(
    estaPulsadoPulgarSuperior: Boolean,
    estaPulsadoPulgarInferior: Boolean,
    estaPulsadoIndice: Boolean,
    estaPulsadoMedio: Boolean,
    estaPulsadoAnular: Boolean,
    estaPulsadoMenique: Boolean
) {
    MostrarImagen(estaPulsadoPulgarSuperior, R.drawable.pulgarsuperiorverde, 248.dp, 175.dp, 100.dp, 100.dp)
    MostrarImagen(estaPulsadoPulgarInferior, R.drawable.pulgarinferiorverde, 183.dp, 245.dp, 110.dp, 110.dp)
    MostrarImagen(estaPulsadoIndice, R.drawable.indiceverde, 120.dp, 28.dp, 235.dp, 235.dp)
    MostrarImagen(estaPulsadoMedio, R.drawable.medioverde, 49.dp, 3.dp, 245.dp, 245.dp)
    MostrarImagen(estaPulsadoAnular, R.drawable.anularverde, 8.dp, 36.dp, 205.dp, 205.dp)
    MostrarImagen(estaPulsadoMenique, R.drawable.meniqueverde, -14.dp, 95.dp, 180.dp, 180.dp)
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
    BotonDedo("Índice", 140.dp, 120.dp, -70f, 200.dp, 35.dp, onDedoPulsado)
    BotonDedo("Medio", 60.dp, 90.dp, 90f, 230.dp, 35.dp, onDedoPulsado)
    BotonDedo("Anular", 10.dp, 90.dp, 75f, 200.dp, 35.dp, onDedoPulsado)
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
