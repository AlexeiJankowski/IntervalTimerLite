package com.hfad.intervaltimerlite.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfad.intervaltimerlite.Screen
import com.hfad.intervaltimerlite.TimeUtils
import com.hfad.intervaltimerlite.TimerLogic
import com.hfad.intervaltimerlite.TimerViewModel
import com.hfad.intervaltimerlite.data.Timer

@Composable
fun TimerList(
    viewModel: TimerViewModel,
    navController: NavController,
    timerLogic: TimerLogic
) {
    Scaffold(
        containerColor = Color(0xFFFFC847),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    navController.navigate(Screen.AddTimerScreen.route + "/0L")
                },
                containerColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new timer",
                    tint = Color.White
                )
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                title = {
                    Text(
                        text = "Interval Timer"
                    )
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
            )
        }
    ) { paddingValues ->
        viewModel.getAllTimers()
        val timers by viewModel.timers.collectAsState(initial = listOf())
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(timers) { timer ->
                TimerItem(
                    timer,
                    onItemClick = {
                        timerLogic.reset()
                        timerLogic.stopTimer()
                        viewModel.onTimerChanged(timer)
                        navController.navigate(Screen.TimerScreen.route)
                        timerLogic.startTimer()
                    },
                    onEditClick = {
                        viewModel.onTimerChanged(timer)
                        navController.navigate(Screen.AddTimerScreen.route + "/${timer.timerId}")
                    },
                    onDeleteClick = {
                        viewModel.deleteTimer(timer)
                    }
                )
            }
        }
    }
}

@Composable
fun TimerItem(
    timer: Timer,
    onItemClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onItemClick()
            },
        elevation = 8.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = timer.timerName.capitalize(), fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "Main timer: ${TimeUtils.timerToHhMmSs(timer.timerMain)}")
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "Rest timer: ${TimeUtils.timerToHhMmSs(timer.timerRest)}")
                Spacer(modifier = Modifier.padding(3.dp))
                Text(text = "Sets: ${timer.timerSets}")
            }
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }
}
