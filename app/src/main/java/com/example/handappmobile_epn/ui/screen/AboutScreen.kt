package com.example.handappmobile_epn.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.example.handappmobile_epn.R


/**
 * Pantalla de "Acerca de" que muestra información de la aplicación, enlaces a redes sociales,
 * y contribuyentes del proyecto.
 *
 * Esta pantalla incluye:
 * - Información sobre la aplicación.
 * - Enlaces a las redes sociales del laboratorio.
 * - Información sobre los contribuyentes con posibilidad de abrir sus perfiles de GitHub.
 * - Botón para abrir enlaces a las redes sociales del laboratorio y el laboratorio de inteligencia artificial.
 *
 * @Composable Esta función es una composición de Jetpack Compose, que gestiona su estado y UI.
 */
@Composable
fun AboutScreen() {
    
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Caja que contiene el nombre y el logo del laboratorio
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .height(180.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp)),   // Efecto de sombra
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hand App",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Laboratorio de Investigación en Inteligencia y Visión Artificial: Alan Turing",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo_hand_app),
                    contentDescription = "Logo",
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        // Caja que contiene los logos de EPN y FIS
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)
                .height(170.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp)),   // Efecto de sombra
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), // Asegura que la Row ocupe toda la altura
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically // Centra verticalmente las imágenes
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_epn),
                    contentDescription = "Logo EPN",
                    modifier = Modifier.size(130.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_fis),
                    contentDescription = "Logo FIS",
                    modifier = Modifier.size(130.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start  // Alinea el contenido a la izquierda
        ){
            Text(
                text = "Enlaces",
                fontSize = 14.sp,
                color = Color.Gray,
            )
        }


        var showDialog by remember { mutableStateOf(false) }

        // Caja que contiene el enlace a la página web del laboratorio y los logos de redes sociales
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp) // Aumenta la altura
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 16.dp)
            ) {
                // Texto centrado verticalmente
                Text(
                    text = "Laboratorio de Inteligencia Artificial",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://laboratorio-ia.epn.edu.ec/es/"))
                            ContextCompat.startActivity(context, intent, null)
                        }
                )

                // Social Media Icons en la parte central
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f) // Reducimos un poco el espacio que ocupa el Row
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.facebook.com/laboratorio.IA.EPN"))
                                ContextCompat.startActivity(context, intent, null)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo_youtube),
                        contentDescription = "YouTube",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.youtube.com/channel/UCWE-OhGIh4rB6bp_MwKWQwQ"))
                                ContextCompat.startActivity(context, intent, null)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo_linkedin),
                        contentDescription = "LinkedIn",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.linkedin.com/company/laboratorio-ia-alan-turing/"))
                                ContextCompat.startActivity(context, intent, null)
                            }
                    )
                }

                // Texto que abre una ventana flotante
                Text(
                    text = "Contribuyentes",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            showDialog = true
                        }
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Contribuyentes")
                },
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.weight(3f) // Distribuye el espacio entre las columnas
                        ) {
                            // Información de Daniel Lorences
                            Text(
                                text = "Daniel Lorences",
                                style = TextStyle(fontWeight = FontWeight.Bold) // Negrilla
                            )
                            Text(
                                text = "jdlorencesv@gmail.com",
                            )

                            Spacer(modifier = Modifier.height(35.dp)) // Espacio entre contribuyentes

                            // Información de Stiven Moposita
                            Text(
                                text = "Stiven Moposita",
                                style = TextStyle(fontWeight = FontWeight.Bold) // Negrilla
                            )
                            Text(
                                text = "stevenmoposita456@gmail.com"
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp)) // Espacio entre columnas

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally // Centra las imágenes
                        ) {
                            // Imagen de GitHub de Daniel Lorences
                            Image(
                                painter = painterResource(id = R.drawable.logo_github),
                                contentDescription = "GitHub",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/Ga1iard")
                                        )
                                        ContextCompat.startActivity(context, intent, null)
                                    }
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            // Imagen de GitHub de Stiven Moposita
                            Image(
                                painter = painterResource(id = R.drawable.logo_github),
                                contentDescription = "GitHub",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/StivenJM")
                                        )
                                        ContextCompat.startActivity(context, intent, null)
                                    }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(
                            text = "Cerrar",
                            color = Color.Black
                        )
                    }
                },
                containerColor = Color.White, // Cambiar el color de fondo completo a blanco
                shape = RoundedCornerShape(20.dp) // Mantener las esquinas redondeadas
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Version Text
        Text(
            text = "versión 1.0.0",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}