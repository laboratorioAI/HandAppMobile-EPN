package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

/**
 * Muestra una imagen en la interfaz de usuario si está habilitada.
 *
 * @param mostrar Indica si la imagen debe ser mostrada.
 * @param imagenRes Recurso de la imagen a mostrar, especificado como un entero que representa el ID del recurso.
 * @param offsetX Desplazamiento en el eje X para la posición de la imagen.
 * @param offsetY Desplazamiento en el eje Y para la posición de la imagen.
 * @param width Ancho de la imagen.
 * @param height Alto de la imagen.
 */
@Composable
fun MostrarImagen(mostrar: Boolean, imagenRes: Int, offsetX: Dp, offsetY: Dp, width: Dp, height: Dp) {
    /* Función que muestra la imagen según el estado */
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