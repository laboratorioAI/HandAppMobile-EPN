import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color

/**
 * Configura las barras del sistema.
 *
 * Esta función se encarga de establecer el color y el estilo de los íconos de la barra de navegación
 * del sistema. Permite personalizar la apariencia de las barras del sistema en la aplicación.
 *
 * Utiliza el controlador de UI del sistema para modificar el color de la barra de navegación y
 * forzar el uso de íconos oscuros, lo que puede mejorar la visibilidad en fondos claros.
 */
@Composable
fun SetSystemBars() {
    val systemUiController = rememberSystemUiController()

    // Forzar los iconos oscuros (negros)
    val useDarkIcons = true

    // Cambia el color de la barra de navegación
    systemUiController.setNavigationBarColor(
        color = Color.White,
        darkIcons = useDarkIcons
    )
}
