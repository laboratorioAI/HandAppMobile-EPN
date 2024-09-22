package com.example.handappmobile_epn.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.handappmobile_epn.R

@Composable
fun PantallaTutorial(onDismiss: () -> Unit) {
    // Declarar el array de 5 elementos con lambdas que invocan composables
    val ventanasTutorial = listOf<@Composable () -> Unit>(
        { PrimeraVentanaTutorial() },
        { SegundaVentanaTutorial() },
        { TerceraVentanaTutorial() },
        { CuartaVentanaTutorial() },
        { QuintaVentanaTutorial() },
        { SextaVentanaTutorial() }
    )

    // Controlar la posición actual en el tutorial
    var posicionActual by remember { mutableStateOf(0) }

    // Contenido del diálogo
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Guía de uso")
        },
        text = {
            Column(
                modifier = Modifier
                    .height(440.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ventanasTutorial[posicionActual]()

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (posicionActual > 0) {
                            Button(
                                onClick = { if (posicionActual > 0) posicionActual-- },
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.app_primary)
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Flecha Izquierda",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (posicionActual < ventanasTutorial.size - 1) {
                            Button(
                                onClick = { if (posicionActual < ventanasTutorial.size - 1) posicionActual++ },
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.app_primary)
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Flecha Derecha",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Button(onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.app_primary)
                        )) {
                        Text("Cerrar")
                    }
                }
            }
        },
        confirmButton = {}, // Dejar vacío para evitar que se muestre el botón predeterminado
        containerColor = Color.White, // Cambiar el color de fondo completo a blanco
        shape = RoundedCornerShape(20.dp) // Mantener las esquinas redondeadas
    )

}

/* Ventana tutorial que corresponde a los permisos del aplicativo */
@Composable
fun PrimeraVentanaTutorial() {
    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Paso 1",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "El aplicativo requiere de permisos Bluetooth para poder funcionar " +
                        "correctamente. \nOtorgue los permisos necesarios en la opción mostrada " +
                        "al momento de iniciar el aplicativo por primera vez.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.permisos_aplicacion),
                contentDescription = "Permisos aplicación",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(240.dp)
                    .height(240.dp)
            )
        }
    }
}

/* Ventana tutorial que corresponde a la conexión bluetooth del equipo */
@Composable
fun SegundaVentanaTutorial() {
    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Paso 2",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Verifique que el Bluetooth del dispositivo esté activado.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.bluetoothactivo),
                contentDescription = "Bluetooth activado",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(300.dp)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Paso 3",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Verifique que el dispositivo se encuentre vinculado a la(s) prótesis.",
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.dispositivosvinculados),
                contentDescription = "Dispositivos vinculados",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .wrapContentSize()
                    .width(400.dp)
                    .height(110.dp)
            )
        }
    }
}

/* Ventana tutorial que corresponde al menú de opciones */
@Composable
fun TerceraVentanaTutorial() {
    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Paso 4",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Para acceder al menú de opciones, pulse el botón ubicado en el " +
                        "área superior izquierda.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.despliegue_menu),
                contentDescription = "Definir movimiento",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(220.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Paso 5",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "En el botón de inicio podrá acceder al control de la mano." +
                        "\nEn el botón de dispositivos podrá acceder a la configuración Bluetooth.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.menu_opciones),
                contentDescription = "Funciones adicionales",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(240.dp)
                    .height(140.dp)
            )
        }
    }
}

/* Ventana tutorial que corresponde a la selección de una mano dentro del aplicativo */
@Composable
fun CuartaVentanaTutorial() {

    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Paso 6",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "En el apartado de dispositivos, encontrará la opción de conectarse a Bluetooth " +
                        "y la prótesis que esté conectada.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.dispositivo_vinculado),
                contentDescription = "Bluetooth activado",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(200.dp)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Paso 7",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Elija la prótesis con la que se desee conectar de entre las opciones en la " +
                        "parte inferior.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.dispositivos_conectados),
                contentDescription = "Elegir prótesis",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(200.dp)
                    .height(120.dp)
            )
        }
    }
}

/* Ventana tutorial que corresponde al uso de la mano */
@Composable
fun QuintaVentanaTutorial() {
    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Paso 8",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "En el menú de inicio, elija los dedos que desee mover. Es importante considerar: " +
                        "\n1. Los dedos no elegidos se mostrarán de color blanco. " +
                        "\n2. Los dedos presionados presentarán un contorno gris. " +
                        "\n3. Los dedos elegidos se mostrarán en color verde.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.seleccionardedos),
                contentDescription = "Seleccionar dedos",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(190.dp)
                    .height(190.dp)
            )
        }
    }
}

/* Ventana tutorial que corresponde a la señalización de funciones adicionales */
@Composable
fun SextaVentanaTutorial() {
    Box(
        modifier = Modifier.fillMaxWidth() // Ocupa completamente el espacio disponible
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Paso 9",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Al mover el slider de izquierda a derecha se definirá el porcentaje de movimiento " +
                        "para los dedos seleccionados.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.definirmovimiento),
                contentDescription = "Definir movimiento",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(220.dp)
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Paso 10",
                style = TextStyle(
                    fontWeight = FontWeight.Bold // Negrilla
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Considere que el aplicativo cuenta con acciones predeterminadas para cada prótesis.",
                modifier = Modifier.fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.opciones_adicionales),
                contentDescription = "Funciones adicionales",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinea la imagen horizontalmente
                    .width(240.dp)
                    .height(120.dp)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewPantallaTutorial() {
    PantallaTutorial(onDismiss = { /* Acción al cerrar */ })
}