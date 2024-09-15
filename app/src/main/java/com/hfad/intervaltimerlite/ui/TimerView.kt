package com.hfad.intervaltimerlite.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.intervaltimerlite.R
import com.hfad.intervaltimerlite.TimerViewModel
import com.hfad.intervaltimerlite.data.Timer
import kotlin.math.ceil

@Composable
fun TimerView(timer: Timer, viewModel: TimerViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TimerInfoColumn(timer, viewModel)

        TimerCounter(viewModel)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.startOver(viewModel.totalTime)
                },
                shape = CircleShape,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Start Over",
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Button(
                onClick = {
                    viewModel.pauseTimer(2000L)
                },
                shape = CircleShape,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Icon(
                    painter = if (viewModel.changePauseButtonState() == "Start")
                        painterResource(id = R.drawable.baseline_play_arrow_24) else
                        painterResource(id = R.drawable.baseline_pause_24),
                    contentDescription = viewModel.changePauseButtonState(),
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun TimerInfoColumn(timer: Timer, viewModel: TimerViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = timer.timerName,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = viewModel.currentTimerState,
            fontSize = 20.sp
        )
        Text(
            text = "Set: ${ceil(viewModel.currentSetState).toInt()}/${timer.timerSets}",
            fontSize = 20.sp
        )
    }
}

@Composable
fun TimerCounter(viewModel: TimerViewModel) {
    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    TimerCircularProgressBar(
        totalTime = viewModel.totalTime,
        remainingTime = viewModel.remainingTimeState,
        viewModel
    )
}

@Composable
fun TimerCircularProgressBar(
    totalTime: Long,
    remainingTime: Long,
    viewModel: TimerViewModel
) {
    val progress = remember(totalTime, remainingTime) {
        mutableStateOf(remainingTime / totalTime.toFloat())
    }

    if (progress.value < 0.03) progress.value = 0f

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {
        CircularProgressIndicator(
            progress = progress.value,
            strokeWidth = 12.dp,
            modifier = Modifier.size(280.dp)
        )
        Text(text = "${(remainingTime / 1000)}s", style = MaterialTheme.typography.h5)
    }
}