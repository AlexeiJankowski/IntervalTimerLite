package com.hfad.intervaltimerlite.data

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun addTimer(timer: Timer)
    suspend fun getAllTimers(): Flow<List<Timer>>
    suspend fun getTimerById(timerId: Long): Flow<Timer>
    suspend fun updateTimer(timer: Timer)
    suspend fun deleteTimer(timer: Timer)
}