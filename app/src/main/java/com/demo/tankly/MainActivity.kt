package com.demo.tankly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.tankly.ui.tanklist.TankListScreen
import com.demo.tankly.ui.tanklist.TankListViewModel
import com.demo.tankly.ui.theme.TanklyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TanklyTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val tankListViewModel: TankListViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TankListScreen(navController = navController, viewModel = tankListViewModel)
        }
    }
}
