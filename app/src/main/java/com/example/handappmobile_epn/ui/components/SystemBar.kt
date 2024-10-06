import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color

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
