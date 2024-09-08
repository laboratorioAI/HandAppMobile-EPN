package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

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