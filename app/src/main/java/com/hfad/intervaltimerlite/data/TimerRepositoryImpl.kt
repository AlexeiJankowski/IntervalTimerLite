package com.hfad.intervaltimerlite.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class TimerRepositoryImpl(private val timerDao: TimerDao) {
    suspend fun addTimer(timer: Timer) {
        timerDao.addTimer(timer)
    }

    fun getAllTimers(): Flow<List<Timer>> {
        return timerDao.getAllTimers()
    }

    fun getTimerById(timerId: Long): Flow<Timer> {
        return timerDao.getTimerById(timerId)
    }

    suspend fun updateTimer(timer: Timer) {
        Log.d("UPDATE2", timer.timerName)
        timerDao.updateTimer(timer)
    }

    suspend fun deleteTimer(timer: Timer) {
        timerDao.deleteTimer(timer)
    }
}