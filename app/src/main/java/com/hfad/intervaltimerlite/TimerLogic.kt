package com.hfad.intervaltimerlite

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hfad.intervaltimerlite.data.Timer

class TimerLogic(
    private val viewModel: TimerViewModel
) {
    val currentTimer: Timer get() = viewModel.currentTimer.value

    var isTimerRunning by mutableStateOf(false)

    var currentSetState by mutableStateOf(0.0)
    var currentTimerState by mutableStateOf("")
    var remainingTimeState by mutableStateOf(0L)

    var currentViewSet by mutableStateOf(0)

    var totalTime by mutableStateOf(0L)

    private var countDownTimer: CountDownTimer? = null

    fun reset() {
        viewModel.onTimerChanged(Timer())
        isTimerRunning = false
        currentSetState = 0.0
        currentTimerState = ""
        remainingTimeState = 0L
        currentViewSet = 0
        totalTime = 0L
        countDownTimer?.cancel()
    }

    fun getCurrentTotalTime() {
        if (((currentSetState * 2) % 2) == 0.0) {
            totalTime = currentTimer.timerMain
            currentTimerState = "Main Timer"
            currentViewSet = (currentSetState + 1).toInt()
        } else {
            totalTime = currentTimer.timerRest
            currentTimerState = "Rest Timer"
            currentViewSet = (currentSetState + 0.5).toInt()
        }
    }

    fun startTimer(duration: Long = 0L) {
        countDownTimer?.cancel()

        var timerDuration: Long

        if (duration != 0L) {
            timerDuration = remainingTimeState
        } else {
            getCurrentTotalTime()
            timerDuration = totalTime
        }

        countDownTimer = object : CountDownTimer(timerDuration, 10) {

            override fun onTick(millisUntilFinished: Long) {
                remainingTimeState = millisUntilFinished
                isTimerRunning = true
            }

            override fun onFinish() {
                if (currentSetState + 0.5 < currentTimer.timerSets.toDouble()) {
                    Log.d("currentSetState < currentTimer.timerSets",
                        "$currentSetState < ${currentTimer.timerSets}")
                    currentSetState += 0.5

                    startTimer()
                } else {
                    stopTimer()
                }
            }
        }

        countDownTimer?.start()
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    private fun resumeTimer(timerDuration: Long) {
        if (remainingTimeState == 0L) {
            startTimer(timerDuration)
        } else {
            startTimer(remainingTimeState)
        }
    }

    fun pauseTimer(timerDuration: Long) {
        if (isTimerRunning) {
            stopTimer()
        } else {
            resumeTimer(timerDuration)
        }
    }

    fun changePauseButtonState(): String {
        return if(isTimerRunning) {
            "Pause"
        } else {
            "Start"
        }
    }

    fun startOver(duration: Long) {
        stopTimer()
        remainingTimeState = totalTime
        startTimer(duration)
    }
}