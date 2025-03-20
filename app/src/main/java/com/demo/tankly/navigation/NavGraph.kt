package com.demo.tankly.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demo.tankly.ui.tanklist.TankListScreen
import com.demo.tankly.ui.tanklist.TankListViewModel
// TODO implement tank details
// import com.demo.tankly.ui.tankdetails.TankDetailsScreen
// import com.demo.tankly.ui.tankdetails.TankDetailsViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val tankListViewModel: TankListViewModel = hiltViewModel()
            TankListScreen(navController = navController, viewModel = tankListViewModel)
        }

        // TODO implement tank details screen
        composable("details/{tankId}") { backStackEntry ->
            val tankId = backStackEntry.arguments?.getString("tankId")?.toIntOrNull() ?: -1
//            val tankDetailsViewModel: TankDetailsViewModel = hiltViewModel()
//            TankDetailsScreen(navController = navController, tankId = tankId, viewModel = tankDetailsViewModel)
        }
    }
}
