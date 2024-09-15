package com.example.handappmobile_epn.ui.screen

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DevicesScreen(bluetoothConnectionManager: BluetoothConnectionManager)
{
    val context = LocalContext.current
    var isBluetoothOn by remember { mutableStateOf(true) }
    isBluetoothOn = bluetoothConnectionManager.isBluetoothOn()

    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    val bluetoothOnLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        isBluetoothOn = result.resultCode == Activity.RESULT_OK
    }

    val disableBtIntent = Intent("android.bluetooth.adapter.action.REQUEST_DISABLE")
    val bluetoothOffLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        isBluetoothOn = !(result.resultCode == Activity.RESULT_OK)
    }

    var showDevicesList by remember { mutableStateOf(true)}
    var isLoading by remember { mutableStateOf(false) }

    var connectedDeviceName by remember { mutableStateOf("Dispositivo") }

    val auxDeviceName = bluetoothConnectionManager.getNameDeviceConnected()
    if (auxDeviceName != null) connectedDeviceName = auxDeviceName else connectedDeviceName = "Dispositivo"

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(20.dp, 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(30.dp, 15.dp)
        )
        {
            // Bluetooth switch section
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "Bluetooth",
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )
                Switch(
                    checked = isBluetoothOn,
                    onCheckedChange = {
                        if (!bluetoothConnectionManager.isBluetoothOn()) bluetoothOnLauncher.launch(enableBtIntent)
                        else bluetoothOffLauncher.launch(disableBtIntent)
                    },
                    modifier = Modifier
                        .requiredSize(16.dp)
                        .padding(end = 100.dp)
                        .scale(0.6f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = colorResource(id = R.color.app_green),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFBBBBBB),
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Connected status
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Conectado",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                if (isLoading)
                {
                    CircularProgressIndicator(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        color = Color(0xFFBBBBBB)
                    )
                } else {
                    Text(
                        text = connectedDeviceName,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Paired devices section
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Dispositivos sincronizados",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            IconButton(
                onClick = {
                    showDevicesList = false
                    showDevicesList = true
                },
                modifier = Modifier.requiredSize(13.dp))
            {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Reload"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(if (isLoading) Color(0xFFDDDDDD) else Color.White)
                .padding(0.dp, 15.dp))
        {
            if (showDevicesList) {
                // List of devices
                val devicesList = BluetoothHelper.getPairedDevices(context, bluetoothConnectionManager.getBluetoothAdapter())
                var success by remember { mutableStateOf(false) }
                devicesList?.forEach { (key, value) ->
                    PairedDeviceItem(deviceName = key, enabled = !isLoading) {
                        scope.launch(Dispatchers.IO) {
                            isLoading = true
                            success = bluetoothConnectionManager.connectToDevice(value)
                            isLoading = false
                            withContext(Dispatchers.Main) {
                                if (!success) Toast.makeText(context, "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show()
                                else Toast.makeText(context, "CONEXIÓN EXITOSA", Toast.LENGTH_LONG).show()

                                val auxName = bluetoothConnectionManager.getNameDeviceConnected()
                                if (auxName != null) connectedDeviceName = auxName else connectedDeviceName = "Dispositivo"
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PairedDeviceItem(deviceName: String, enabled:Boolean = true, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Black
        )
    ) {
        Text(
            text = deviceName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBluetoothScreen() {
    DevicesScreen(
        bluetoothConnectionManager = BluetoothConnectionManager(null)
    )
}