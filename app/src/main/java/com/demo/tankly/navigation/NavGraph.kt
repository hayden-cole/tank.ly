package com.demo.tankly.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demo.tankly.ui.tankdetail.TankDetailScreen
import com.demo.tankly.ui.tankdetail.TankDetailViewModel
import com.demo.tankly.ui.tanklist.TankListScreen
import com.demo.tankly.ui.tanklist.TankListViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "tankList") {
        composable("tankList") {
            val tankListViewModel: TankListViewModel = hiltViewModel()
            TankListScreen(navController = navController, viewModel = tankListViewModel)
        }

        composable("tankDetails/{tankId}") { backStackEntry ->
            val tankId = backStackEntry.arguments?.getString("tankId")?.toIntOrNull() ?: -1
            val tankDetailViewModel: TankDetailViewModel = hiltViewModel()
            TankDetailScreen(
                navController = navController,
                tankId = tankId,
                viewModel = tankDetailViewModel
            )
        }
    }
}
