package com.demo.tankly.ui.tankdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage

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
            Text (
                // TODO would be nice to make this smoother
                text = "Tank Details: \n${tank?.name ?: "..."}",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (tank == null) {
                Text("Spinning up...", style = MaterialTheme.typography.bodyMedium)
            } else {
                AsyncImage(
                    model = tank.imageUrl,
                    contentDescription = "A picture of the ${tank.name} tank.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(250.dp)
                )
                tank.let {
                    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
                        DetailRow(label = "Name", value = it.name)
                        DetailRow(label = "Country of Origin", value = it.country)
                        DetailRow(label = "Year Manufactured", value = it.yearManufactured.toString())
                        DetailRow(label = "Type", value = it.type)
                    }
                }
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .border(2.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "BACK",
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
