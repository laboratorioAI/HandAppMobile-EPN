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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.handappmobile_epn.R

/**
 * Controlador de la mano
 *
 * @param estaPulsadoPulgarSuperior Indica si el pulgar superior está pulsado.
 * @param estaPulsadoPulgarInferior Indica si el pulgar inferior está pulsado.
 * @param estaPulsadoIndice Indica si el dedo índice está pulsado.
 * @param estaPulsadoMedio Indica si el dedo medio está pulsado.
 * @param estaPulsadoAnular Indica si el dedo anular está pulsado.
 * @param estaPulsadoMenique Indica si el meñique está pulsado.
 * @param habilitar Determina si los botones de los dedos están habilitados.
 * @param onDedoPulsado Función que se invoca cuando un dedo es pulsado, recibe el nombre del dedo y su estado.
 * @receiver
 */
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

/**
 * Imágenes de los dedos
 *
 * @param estaPulsadoPulgarSuperior Indica si el pulgar superior está pulsado.
 * @param estaPulsadoPulgarInferior Indica si el pulgar inferior está pulsado.
 * @param estaPulsadoIndice Indica si el dedo índice está pulsado.
 * @param estaPulsadoMedio Indica si el dedo medio está pulsado.
 * @param estaPulsadoAnular Indica si el dedo anular está pulsado.
 * @param estaPulsadoMenique Indica si el meñique está pulsado.
 */
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

/**
 * Botones de la mano
 *
 * @param onDedoPulsado Función que se invoca cuando un dedo es pulsado, recibe el nombre del dedo y su estado.
 * @receiver
 */
@Composable
fun HandButtons(onDedoPulsado: (String, Boolean) -> Unit) {
    LogicaBotonesMano(
        onDedoPulsado = onDedoPulsado
    )
}

/**
 * Lógica de los botones de la mano
 *
 * @param onDedoPulsado Función que se invoca cuando un dedo es pulsado, recibe el nombre del dedo y su estado.
 * @receiver
 */
@Composable
fun LogicaBotonesMano(onDedoPulsado: (String, Boolean) -> Unit) {
    /* Creación de los botones de la mano */
    // Botones y lógica de HandiEpn aquí
    BotonDedo("Pulgar Superior", 270.dp, 210.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Pulgar Inferior", 210.dp, 280.dp, -45f, 90.dp, 40.dp, onDedoPulsado)
    BotonDedo("Índice", 140.dp, 120.dp, -72f, 210.dp, 40.dp, onDedoPulsado)
    BotonDedo("Medio", 60.dp, 90.dp, 90f, 230.dp, 35.dp, onDedoPulsado)
    BotonDedo("Anular", 10.dp, 90.dp, 75f, 200.dp, 35.dp, onDedoPulsado)
    BotonDedo("Meñique", (-20).dp, 140.dp, 55f, 155.dp, 35.dp, onDedoPulsado)
}

/**
 * Botón de dedo
 *
 * @param nombre Nombre del dedo asociado al botón.
 * @param offsetX Desplazamiento en el eje X del botón.
 * @param offsetY Desplazamiento en el eje Y del botón.
 * @param rotacion Ángulo de rotación del botón.
 * @param width Ancho del botón.
 * @param height Alto del botón.
 * @param onClick Función que se invoca cuando se hace clic en el botón, recibe el nombre del dedo y su estado.
 * @receiver
 */
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
    /* Función para declarar los datos de los botones de los dedos */
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
