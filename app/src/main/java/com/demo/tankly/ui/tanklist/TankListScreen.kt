package com.demo.tankly.ui.tanklist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TankListScreen(navController: NavController, viewModel: TankListViewModel = viewModel()) {
    val tankList = viewModel.tankList.observeAsState(emptyList())
    val context = LocalContext.current
    var isRefreshing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Text("Tank List", modifier = Modifier.padding(16.dp)) }
    ) { paddingValues ->
        PullToRefreshBox (
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.refreshTanks()

                Toast.makeText(context, "You have the latest data.", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(tankList.value) { tank ->
                    TankItem(
                        tank = tank,
                        onClick = { navController.navigate("tankDetails/${tank.id}") }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.tankList.observeForever {
            isRefreshing = false
        }
//        viewModel.refreshTanks()
    }
}