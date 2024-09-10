package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager
import com.example.handappmobile_epn.ui.screen.DevicesScreen

@Composable
fun BackScaffoldContent(
    menuText: String = "Menu",
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { BackToolBar(menuText) }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToolBar(menuText: String = "Menu") {
    TopAppBar(
        title = { Text(text = menuText) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.app_primary),
            navigationIconContentColor = colorResource(id = R.color.white),
            titleContentColor = colorResource(id = R.color.white),
            actionIconContentColor = colorResource(id = R.color.white)
        ),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}
