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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.components.BackScaffoldContent
import com.example.handappmobile_epn.ui.components.ViewContainer


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
        // Top Section
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
                        text = "Laboratorio de Inteligencia\n Artificial: Alan Turing",
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

        // Middle Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp)
                .height(180.dp)
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
                    modifier = Modifier.size(140.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_fis),
                    contentDescription = "Logo FIS",
                    modifier = Modifier.size(140.dp)
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

        // Link Section
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp)),   // Efecto de sombra
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
                        //.weight(1f) // Ocupa el espacio restante para empujar el Row hacia abajo
                        .align(Alignment.CenterHorizontally) // Centrado horizontal
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://laboratorio-ia.epn.edu.ec/es/"))
                            ContextCompat.startActivity(context, intent, null)
                        }
                )

                // Social Media Icons en la parte inferior
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 8.dp), // Padding opcional arriba del Row
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
                        contentDescription = "Instagram",
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
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Version Text
        Text(
            text = "versión 1.0.0",
            fontSize = 12.sp,
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