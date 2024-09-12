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
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.ui.components.BackScaffoldContent
import com.example.handappmobile_epn.ui.components.ViewContainer

@Composable
fun SettingsScreen()
{
    var isDebugModeOn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
            .padding(30.dp, 40.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start)
    {
        Text(
            text = "Depuración",
            color = colorResource(id = R.color.app_primary),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

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
            Switch(
                checked = isDebugModeOn,
                onCheckedChange = { isDebugModeOn = it },
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
    }
}

@Preview(showBackground = true)
@Composable
fun AjustesPreview()
{
    SettingsScreen()
}