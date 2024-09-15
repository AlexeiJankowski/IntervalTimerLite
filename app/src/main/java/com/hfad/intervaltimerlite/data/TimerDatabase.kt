package com.hfad.intervaltimerlite.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Timer::class],
    version = 1,
    exportSchema = false
)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}