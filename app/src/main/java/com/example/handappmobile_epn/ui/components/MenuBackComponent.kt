package com.example.handappmobile_epn.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.example.handappmobile_epn.R
import com.example.handappmobile_epn.bt.BluetoothConnectionManager

//@Composable
//fun BackScaffoldContent(bluetoothConnectionManager: BluetoothConnectionManager) {
//    Scaffold(modifier = Modifier.fillMaxSize(),
//        topBar = { BackToolBar() }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            HomeContentScreen(
//                bluetoothConnectionManager,
//                modifier = Modifier.padding(innerPadding)
//            )
//        }
//
//    }
//}

@Composable
fun BackScaffoldContent(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier= Modifier.fillMaxSize(),
        topBar = { BackToolBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            content(innerPadding)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToolBar() {
    TopAppBar(
        title = { Text(text = "Hand App") },
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
