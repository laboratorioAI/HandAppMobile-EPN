package com.example.handappmobile_epn.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.utils.DebugSettings
import kotlinx.coroutines.delay
import java.io.IOException

@Composable
fun DebugScreen(bluetoothConnectionManager: BluetoothConnectionManager)
{
    val maxMessages = 100 // Número máximo de mensajes que se mostrará
    var messages by remember { mutableStateOf(listOf("Conectando...")) }
    val listState = rememberLazyListState()

    if (bluetoothConnectionManager.isConnected() && DebugSettings.isDebugModeOn) {
        LaunchedEffect(Unit) {
            while (true) {
                try {
                    val receivedMessage = bluetoothConnectionManager.receiveMessage()

                    // Update messages list only if there is a message
                    if (!receivedMessage.isNullOrEmpty()) {
                        messages = (messages + receivedMessage).takeLast(maxMessages)

                        // Automatic scroll at the end
                        listState.animateScrollToItem(messages.size - 1)
                    }

                    delay(200) // To avoid overloading the device and block the screen
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(20.dp, 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "Mensajes Recibidos",
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        )

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(colorResource(id = R.color.app_dark))
                .padding(15.dp, 15.dp),
            verticalArrangement = Arrangement.Bottom,
            state = listState
        ){
            items(messages.size) { index ->
                Text(
                    text = messages[index],
                    color = Color(0xFF00FF30),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DebugPreview()
{
    DebugScreen(bluetoothConnectionManager = BluetoothConnectionManager(null))
}