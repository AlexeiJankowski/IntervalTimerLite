package com.hfad.intervaltimerlite

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.intervaltimerlite.data.DummyTimer
import com.hfad.intervaltimerlite.data.Timer
import com.hfad.intervaltimerlite.data.TimerRepository
import com.hfad.intervaltimerlite.ui.TimerCounter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerViewModel(private val timerRepository: TimerRepository = Graph.timerRepository): ViewModel() {
//    States
    var timerNameState by mutableStateOf("")
    var timerSetsState by mutableStateOf(0)
    var timerMainState by mutableStateOf(0L)
    var timerRestState by mutableStateOf(0L)

    var totalTime by mutableStateOf(0L)

    var isTimerRunning by mutableStateOf(false)

    var currentSetState by mutableStateOf(0.0)
    var currentTimerState by mutableStateOf("")
    var remainingTimeState by mutableStateOf(0L)

    private var countDownTimer: CountDownTimer? = null


    // Flow for handling timers list
    var getAllTimers = mutableStateOf<List<Timer>>(emptyList())
        private set

    // Initialization
    init {
//        viewModelScope.launch(Dispatchers.IO) {
            getAllTimers.value = DummyTimer.timerList // or use repository call if connected
//        }
    }

    fun getCurrentTotalTime() {
        if (((currentSetState * 2) % 2) != 0.0) {
            totalTime = timerMainState
            currentTimerState = "Main Timer"
        } else {
            totalTime = timerRestState
            currentTimerState = "Rest Timer"
        }
    }

    fun onTimerNameChanged(newString: String) {
        timerNameState = newString
    }

    fun onTimerSetsChanged(newNumber: Int) {
        timerSetsState = newNumber
    }

    fun onTimerMainChanged(newNumber: Long) {
        timerMainState = newNumber
    }

    fun onTimerRestChanged(newNumber: Long) {
        timerRestState = newNumber
    }

//    Working with database
    fun addTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            timerRepository.addTimer(timer = timer)
        }
    }

    fun getTimerById(id: Long): Flow<Timer> {
        return timerRepository.getTimerById(id)
    }

    fun updateTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            timerRepository.updateTimer(timer = timer)
        }
    }

    fun deleteTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            timerRepository.deleteTimer(timer = timer)
        }
    }

    fun startTimer(duration: Long = 0L) {

        countDownTimer = object : CountDownTimer(duration, 10) {

            override fun onTick(millisUntilFinished: Long) {
                remainingTimeState = millisUntilFinished
                isTimerRunning = true
            }

            override fun onFinish() {
                if (currentSetState < timerSetsState) {
                    currentSetState += 0.5

                    getCurrentTotalTime()
                    startTimer(totalTime)
                }

                isTimerRunning = false
            }
        }

        countDownTimer?.start()
    }

    private fun stopTimer() {
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
        startTimer(duration)
    }
}