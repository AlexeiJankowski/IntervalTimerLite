package com.hfad.intervaltimerlite.ui

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hfad.intervaltimerlite.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfad.intervaltimerlite.Screen
import com.hfad.intervaltimerlite.data.Timer

@Composable
fun AddEditTimer(
    id: Long,
    viewModel: TimerViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.timerNameState,
                onValueChange = { newText -> viewModel.onTimerNameChanged(newText) },
                label = { Text("Timer Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.timerMainState.toString(),
                onValueChange = { newText -> viewModel.onTimerMainChanged(newText.toLong()) },
                label = { Text("Main Timer Duration") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.timerRestState.toString(),
                onValueChange = { newText -> viewModel.onTimerRestChanged(newText.toLong()) },
                label = { Text("Main Timer Duration") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.timerSetsState.toString(),
                onValueChange = { newText -> viewModel.onTimerSetsChanged(newText.toInt()) },
                label = { Text("Number of Sets") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    Toast.makeText(context, "Submitted successfully", Toast.LENGTH_LONG).show()
                }) {
                    Text(
                        text = "Save and Start",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    onClick = {
                        val timer = Timer(
                            timerName = viewModel.timerNameState,
                            timerSets = viewModel.timerSetsState,
                            timerMain = viewModel.timerMainState,
                            timerRest = viewModel.timerRestState
                        )

                        val bundle = Bundle().apply {
                            putParcelable("timer", timer)
                        }

                        navController.navigate(Screen.TimerScreen.route) {
                            navController.currentBackStackEntry?.arguments?.putAll(bundle)
                        }
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