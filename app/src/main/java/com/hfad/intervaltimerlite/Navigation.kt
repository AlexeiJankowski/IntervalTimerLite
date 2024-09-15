package com.hfad.intervaltimerlite

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hfad.intervaltimerlite.data.Timer
import com.hfad.intervaltimerlite.ui.AddEditTimer
import com.hfad.intervaltimerlite.ui.TimerList
import com.hfad.intervaltimerlite.ui.TimerView

@Composable
fun Navigation(
    viewModel: TimerViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.TimerListScreen.route
    ) {
        composable(Screen.TimerListScreen.route) {
            TimerList(navController = navController)
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
            AddEditTimer(id = id, viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.TimerScreen.route,
//            arguments = listOf(navArgument("timer") {
//                type = NavType.ParcelableType(Timer::class.java)
//            })
        ) { backStackEntry ->
            val timer = navController.previousBackStackEntry?.arguments?.getParcelable<Timer>("timer")
            Log.d("TIMERNAV", "$timer")
            if (timer != null) {
                TimerView(timer, viewModel)
                Log.d("TIMERNAV", "${timer.timerId} ${timer.timerName} ${timer.timerSets}")
            } else {
                Toast.makeText(context, "Error!!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}