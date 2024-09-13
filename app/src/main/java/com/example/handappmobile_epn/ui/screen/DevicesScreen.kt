package com.example.handappmobile_epn.ui.screen

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.bt.BluetoothHelper

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
                    modifier = Modifier.scale(0.7f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = colorResource(id = R.color.app_green),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFBBBBBB),
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                Text(
                    text = "Dispositivo",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Paired devices section
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dispositivos sincronizados",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            IconButton(onClick = { /*TODO*/ }) {

            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(30.dp, 15.dp))
        {
            // List of devices
            PairedDeviceItem(deviceName = "HANDI_EPN")
            Spacer(modifier = Modifier.height(8.dp))
            PairedDeviceItem(deviceName = "FLEXIBLE_B2")
            val devicesList = BluetoothHelper.getPairedDevices(context, bluetoothConnectionManager.getBluetoothAdapter())

        }
    }
}

@Composable
fun PairedDeviceItem(deviceName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(0.dp, 16.dp)
    ) {
        Text(
            text = deviceName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
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