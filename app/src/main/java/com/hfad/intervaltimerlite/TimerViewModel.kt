package com.hfad.intervaltimerlite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.intervaltimerlite.data.Timer
import com.hfad.intervaltimerlite.data.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel(
    private val timerRepository: TimerRepository
): ViewModel() {

    private val _timers = MutableStateFlow<List<Timer>>(emptyList())
    val timers: StateFlow<List<Timer>> get() = _timers

    private val _currentTimer = MutableStateFlow(Timer())
    val currentTimer: StateFlow<Timer> get() = _currentTimer

    init {
        getAllTimers()
    }

    fun onTimerChanged(newTimer: Timer) {
        _currentTimer.value = newTimer
    }

//    Working with database
    fun getAllTimers() {
        viewModelScope.launch {
            timerRepository.getAllTimers().collect {
                _timers.value = it
            }
        }
    }

    fun addTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            timerRepository.addTimer(timer = timer)
        }
    }

    fun getTimerById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            timerRepository.getTimerById(id).collect() {
                _currentTimer.value = it
            }
        }
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
        getAllTimers()
    }
}