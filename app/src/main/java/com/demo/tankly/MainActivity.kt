package com.demo.tankly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.tankly.ui.tankdetail.TankDetailScreen
import com.demo.tankly.ui.tankdetail.TankDetailViewModel
import com.demo.tankly.ui.tanklist.TankListScreen
import com.demo.tankly.ui.tanklist.TankListViewModel
import com.demo.tankly.ui.theme.TanklyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TanklyTheme(darkTheme = true, dynamicColor = false) {
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

    NavHost(navController = navController, startDestination = "tankList") {
        composable("tankList") {
            TankListScreen(navController = navController, viewModel = tankListViewModel)
        }

        composable("tankDetails/{tankId}",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(200, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(200, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
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
