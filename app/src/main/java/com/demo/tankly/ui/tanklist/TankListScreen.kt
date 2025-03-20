package com.demo.tankly.ui.tanklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.demo.tankly.data.model.Tank

@Composable
fun TankItem(
    tank: Tank,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = tank.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = "Country of Origin: ${tank.country}", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun TankListScreen(navController: NavController, viewModel: TankListViewModel = viewModel()) {
    val tankList = viewModel.tankList.observeAsState(emptyList())

    Scaffold(
        topBar = { Text("Tank List") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { viewModel.refreshTanks() }) {
                Text("Refresh Tanks")
            }

            LazyColumn {
                items(tankList.value) { tank ->
                    TankItem(
                        tank = tank,
                        onClick = { navController.navigate("details/${tank.id}") }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refreshTanks()
    }
}