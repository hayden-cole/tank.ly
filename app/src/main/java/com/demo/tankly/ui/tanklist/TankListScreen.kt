package com.demo.tankly.ui.tanklist

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.demo.tankly.data.model.Tank

val countryColors = mapOf(
    "USSR" to Color(0xFFD32F2F), // Red
    "United States of America" to Color(0xFF0288D1), // Light Blue
    "United Kingdom" to Color(0xFFFFC107), // Amber
    "Germany" to Color(0xFF388E3C), // Green
    "France" to Color(0xFF9C27B0) // Purple
)

@Composable
fun getCountryColor(country: String): Color {
    return countryColors[country] ?: MaterialTheme.colorScheme.error // Default color if not found
}

@Composable
fun TankItem(
    tank: Tank,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stripeColor = getCountryColor(tank.country)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(IntrinsicSize.Min)
            .clickable(onClick = onClick),
        shape = RectangleShape
    ) {
        Row() {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(stripeColor)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = tank.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = tank.country, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier
                .weight(1f)
            )
            // Hint
            Text(
                text = ">",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color(0x80FFFFFF)),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(all = 20.dp)
            )
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
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .padding(all = 4.dp)
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "TANK.ly",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
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