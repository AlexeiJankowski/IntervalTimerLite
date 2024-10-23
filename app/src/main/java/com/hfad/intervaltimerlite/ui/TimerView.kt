package com.hfad.intervaltimerlite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.intervaltimerlite.R
import com.hfad.intervaltimerlite.TimeUtils
import com.hfad.intervaltimerlite.TimerLogic
import com.hfad.intervaltimerlite.TimerViewModel
import com.hfad.intervaltimerlite.data.Timer

@Composable
fun TimerView(
    timerLogic: TimerLogic,
    viewModel: TimerViewModel,
    onBackPressed: () -> Unit
) {
    val timer by viewModel.currentTimer.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFC847),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                title = {
                    Text(text = timer.timerName.capitalize())
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            timerLogic.stopTimer()
                            onBackPressed()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TimerInfoColumn(timerLogic, timer)
            TimerCounter(timerLogic)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        timerLogic.startOver(timerLogic.totalTime)
                    },
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
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        timerLogic.pauseTimer(timerLogic.totalTime)
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Icon(
                        painter = if (timerLogic.changePauseButtonState() == "Start")
                            painterResource(id = R.drawable.baseline_play_arrow_24) else
                            painterResource(id = R.drawable.baseline_pause_24),
                        contentDescription = timerLogic.changePauseButtonState(),
                        tint = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TimerInfoColumn(
    timerLogic: TimerLogic,
    timer: Timer
) {
    Column(
        modifier = Modifier
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .padding(64.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    text = timerLogic.currentTimerState,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)  // Darker color for better readability
                    ),
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Set: ${(timerLogic.currentViewSet)}/${timer.timerSets}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)  // Lighter color for contrast
                    ),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun TimerCounter(timerLogic: TimerLogic) {
    LaunchedEffect(Unit) {
        timerLogic.startTimer(timerLogic.totalTime)
    }

    TimerCircularProgressBar(
        totalTime = timerLogic.totalTime,
        remainingTime = timerLogic.remainingTimeState
    )
}

@Composable
fun TimerCircularProgressBar(
    totalTime: Long,
    remainingTime: Long
) {
    val progress = remember(totalTime, remainingTime) {
        mutableStateOf(remainingTime / totalTime.toFloat())
    }

    if (progress.value < 0.03) progress.value = 0f

    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .size(360.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = progress.value,
            strokeWidth = 16.dp,
            modifier = Modifier.size(280.dp),
            color = Color.Black
        )
        Text(text = "${TimeUtils.timerToHhMmSs(remainingTime + 900)}", style = MaterialTheme.typography.bodyLarge)
    }
}