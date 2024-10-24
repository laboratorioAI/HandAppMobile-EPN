package com.example.handappmobile_epn.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.utils.DebugSettings

/**
 * Pantalla de configuración de la aplicación.
 *
 * Permite activar o desactivar las opciones de depuración mediante un interruptor.
 */
@Composable
fun SettingsScreen()
{
    // Estado local para el modo de depuración.
    var isDebugModeOn by remember { mutableStateOf(DebugSettings.isDebugModeOn) }

    // Contenedor vertical que permite el desplazamiento.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(30.dp, 40.dp)
            .verticalScroll(rememberScrollState()), // Desplazamiento vertical.
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start)
    {
        // Título de la sección.
        Text(
            text = "Depuración",
            color = colorResource(id = R.color.app_primary),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // Fila con el texto y el interruptor.
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = "Opciones de depuración",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            // Interruptor para activar/desactivar el modo de depuración.
            Switch(
                checked = isDebugModeOn,
                onCheckedChange = {
                    isDebugModeOn = it                  // Estado local.
                    DebugSettings.isDebugModeOn = it    // Estado global.
                },
                modifier = Modifier.scale(0.7f),        // Tamaño del interruptor.
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorResource(id = R.color.app_green),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFBBBBBB),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}