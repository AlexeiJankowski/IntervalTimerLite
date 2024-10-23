package com.hfad.intervaltimerlite.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfad.intervaltimerlite.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfad.intervaltimerlite.Screen
import com.hfad.intervaltimerlite.TimeUtils
import com.hfad.intervaltimerlite.TimeUtils.hhMmSsToMillis
import com.hfad.intervaltimerlite.TimerLogic
import com.hfad.intervaltimerlite.data.Timer

@Composable
fun AddEditTimer(
    id: Long,
    viewModel: TimerViewModel = viewModel(),
    navController: NavController,
    onBackPressed: () -> Unit,
    timerLogic: TimerLogic
) {
    val context = LocalContext.current

    val timer by viewModel.currentTimer.collectAsState()

    var timerNameState by remember { mutableStateOf(timer.timerName) }
    var timerMainState by remember { mutableStateOf(TimeUtils.timerToHhMmSs(timer.timerMain)) }
    var timerRestState by remember { mutableStateOf(TimeUtils.timerToHhMmSs(timer.timerRest)) }
    var timerSetsState by remember { mutableStateOf(timer.timerSets.toString()) }

    if (id == 0L) {
        timerNameState = ""
        timerMainState = "00:00:00"
        timerRestState = "00:00:00"
        timerSetsState = "0"
    }

    var title by remember { mutableStateOf("") }

    title = if (id != 0L) {
        viewModel.getTimerById(id)
        "Edit ${timer.timerName}"
    } else {
        "Add a New Timer"
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                modifier = Modifier.height(60.dp),
                title = {
                    Text(text = title)
                },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
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
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(bottom = 0.dp)
                .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .background(Color.White)
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = timerNameState,
                        onValueChange = { newName ->
                            timerNameState = newName
                            viewModel.onTimerChanged(timer.copy(timerName = newName))
                        },
                        label = { Text("Timer Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = timerMainState,
                        onValueChange = { newMainTime ->
                            timerMainState = newMainTime
                            viewModel.onTimerChanged(
                                timer.copy(timerMain = TimeUtils.hhMmSsToMillis(TimeUtils.formatToHhMmSs(TimeUtils.deleteDots(newMainTime))))
                            )
                        },
                        label = { Text("Main Timer Duration") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = timerRestState,
                        onValueChange = { newRestTime ->
                            timerRestState = newRestTime
                            viewModel.onTimerChanged(
                                timer.copy(timerRest = TimeUtils.hhMmSsToMillis(TimeUtils.formatToHhMmSs(TimeUtils.deleteDots(newRestTime))))
                            )
                        },
                        label = { Text("Rest Timer Duration") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = timerSetsState,
                        onValueChange = { newSets ->
                            timerSetsState = newSets
                            viewModel.onTimerChanged(
                                timer.copy(timerSets = newSets.toIntOrNull() ?: timer.timerSets) // Handle invalid input
                            )
                        },
                        label = { Text("Number of Sets") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        if(id != 0L) {
                            viewModel.updateTimer(
                                Timer(
                                    timerName = timerNameState,
                                    timerSets = timerSetsState.toInt(),
                                    timerRest = hhMmSsToMillis(timerRestState),
                                    timerMain = hhMmSsToMillis(timerMainState),
                                    timerId = id
                                )
                            )
                        } else {
                            viewModel.addTimer(
                                Timer(
                                    timerName = timerNameState,
                                    timerSets = timerSetsState.toInt(),
                                    timerRest = hhMmSsToMillis(timerRestState),
                                    timerMain = hhMmSsToMillis(timerMainState)
                                )
                            )
                        }
                        timerLogic.stopTimer()
                        timerLogic.startTimer()
                    }
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        timerLogic.reset()
                        viewModel.onTimerChanged(
                            Timer(
                                timerName = timerNameState,
                                timerSets = timerSetsState.toInt(),
                                timerRest = hhMmSsToMillis(timerRestState),
                                timerMain = hhMmSsToMillis(timerMainState)
                            )
                        )
                        timerLogic.startTimer()
                        navController.navigate(Screen.TimerScreen.route)
                    }
                ) {
                    Text(
                        text = "Start",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

