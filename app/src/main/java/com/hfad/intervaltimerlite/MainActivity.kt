package com.hfad.intervaltimerlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.hfad.intervaltimerlite.ui.theme.IntervalTimerLiteTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TimerViewModel = koinViewModel()
            val timerLogic = TimerLogic(viewModel)

            IntervalTimerLiteTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = { paddingValues ->
                        Navigation(
                            modifier = Modifier
                                .padding(paddingValues),
                            viewModel = viewModel,
                            timerLogic = timerLogic
                        )
                    }
                )
            }
        }
    }
}