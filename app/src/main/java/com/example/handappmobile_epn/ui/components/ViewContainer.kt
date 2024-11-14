package com.example.handappmobile_epn.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.handappmobile_epn.ui.theme.HandAppMobileEPNTheme

/**
 * Contenedor de vista.
 *
 * Esta función es un contenedor composable que aplica el tema de la aplicación y configura
 * el esquema de colores para el contenido de la vista. Se utiliza para envolver los componentes
 * de la interfaz de usuario en un tema específico, garantizando la consistencia en el diseño
 * de la aplicación.
 *
 * @param content Función composable que representa el contenido a mostrar dentro del contenedor.
 * @receiver Esta función se invoca como un composable, permitiendo que se inserte contenido
 * adicional dentro de su ámbito de aplicación.
 */
@Composable
fun ViewContainer(
    content: @Composable () -> Unit)
{
    HandAppMobileEPNTheme {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = Color(0xFFFFFFFF) //Blanco
            )
        ) {
            content()
        }
    }
}