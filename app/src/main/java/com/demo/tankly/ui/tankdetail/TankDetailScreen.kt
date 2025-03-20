package com.demo.tankly.ui.tankdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun TankDetailScreen(
    navController: NavController,
    tankId: Int,
    viewModel: TankDetailViewModel = viewModel()
) {
    val tank = viewModel.tank.observeAsState().value

    LaunchedEffect(tankId) {
        viewModel.getTank(tankId)
    }

    Scaffold(
        topBar = {
            Text(
                text = "Tank Details",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            if (tank == null) {
                Text("Loading tank details...", style = MaterialTheme.typography.bodyMedium)
            } else {
                tank.let {
                    Text("Tank Name: ${it.name}", style = MaterialTheme.typography.headlineMedium)
                    Text("Country of Origin: ${it.country}", style = MaterialTheme.typography.bodyLarge)
                    Text("Year Manufactured: ${it.yearManufactured}", style = MaterialTheme.typography.bodyLarge)
                    Text("Type: ${it.type}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
