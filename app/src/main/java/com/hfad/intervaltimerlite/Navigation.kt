package com.hfad.intervaltimerlite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hfad.intervaltimerlite.ui.AddEditTimer
import com.hfad.intervaltimerlite.ui.TimerList
import com.hfad.intervaltimerlite.ui.TimerView

@Composable
fun Navigation(
    modifier: Modifier,
    viewModel: TimerViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    timerLogic: TimerLogic
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TimerListScreen.route
    ) {
        composable(Screen.TimerListScreen.route) {
            TimerList(viewModel, navController, timerLogic)
        }
        composable(Screen.AddTimerScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditTimer(
                id = id,
                viewModel = viewModel,
                navController = navController,
                onBackPressed = {
                    navController.popBackStack()
                    timerLogic.stopTimer()
                },
                timerLogic = timerLogic
            )
        }
        composable(
            route = Screen.TimerScreen.route
        ) {
            TimerView(
                timerLogic,
                viewModel,
                onBackPressed = {
                    navController.popBackStack(Screen.TimerListScreen.route, inclusive = false)
                    timerLogic.stopTimer()
                }
            )
        }
    }
}