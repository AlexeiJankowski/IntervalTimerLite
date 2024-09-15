package com.hfad.intervaltimerlite.ui

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfad.intervaltimerlite.Screen
import com.hfad.intervaltimerlite.data.DummyTimer
import com.hfad.intervaltimerlite.data.Timer



@Composable
fun TimerList(
    navController: NavController
) {
    val timers = DummyTimer.timerList
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    navController.navigate(Screen.AddTimerScreen.route + "/0L")
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new timer")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(timers) { timer ->
                TimerItem(timer) {
                    putTimerIntoBundleAndSend(timer, navController)
                }
            }
        }
    }
}

fun putTimerIntoBundleAndSend(timer: Timer, navController: NavController) {
    Log.d("TIMER", timer.timerName)
    val bundle = Bundle().apply {
        putParcelable("timer", timer)
    }

    val timer2 = bundle.getParcelable<Timer>("timer")

//    navController.navigate(Screen.TimerScreen.route) {
//        navController.currentBackStackEntry?.arguments?.putAll(bundle)
//    }

    Log.d("TIMERUNPACKED", timer2!!.timerName)
}

@Composable
fun TimerItem(
    timer: Timer,
    onItemClick: () -> Unit
) {
    Log.d("TimerItem", timer.timerName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable{
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = timer.timerName, fontWeight = FontWeight.ExtraBold)
            Text(text = "Main timer: ${timer.timerMain}")
            Text(text = "Rest timer: ${timer.timerRest}")
            Text(text = "Sets: ${timer.timerSets}")
        }
    }
}
