package com.hfad.intervaltimerlite.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTimer(timerEntity: Timer)

    @Query("SELECT * FROM `timer-table`")
    abstract fun getAllTimers(): Flow<List<Timer>>

    @Update
    abstract suspend fun updateTimer(timerEntity: Timer)

    @Delete
    abstract suspend fun deleteTimer(timerEntity: Timer)

    @Query("SELECT * FROM `timer-table` WHERE timerId = :id")
    abstract fun getTimerById(id: Long): Flow<Timer>
}