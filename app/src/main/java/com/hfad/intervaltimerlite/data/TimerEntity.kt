package com.hfad.intervaltimerlite.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer-table")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    var timerId: Long = 0L,
    @ColumnInfo(name = "name")
    var timerName: String = "",
    @ColumnInfo(name = "sets")
    var timerSets: Int = 0,
    @ColumnInfo(name = "timer_main")
    var timerMain: Long = 0,
    @ColumnInfo(name = "timer_rest")
    var timerRest: Long = 0,
)
