package com.hfad.intervaltimerlite.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "timer-table")
data class Timer(
    @PrimaryKey(autoGenerate = true)
    var timerId: Long = 0L,
    @ColumnInfo(name = "timer_name")
    var timerName: String = "",
    @ColumnInfo(name = "timer_sets")
    var timerSets: Int = 0,
    @ColumnInfo(name = "timer_main")
    var timerMain: Long = 0,
    @ColumnInfo(name = "timer_rest")
    var timerRest: Long = 0,
) : Parcelable

object DummyTimer{
    val timerList = listOf(
        Timer(
            timerId = 0,
            timerName = "Workout 1",
            timerSets = 3,
            timerMain = 2500,
            timerRest = 1200
        ),
        Timer(
            timerId = 0,
            timerName = "Workout 2",
            timerSets = 2,
            timerMain = 3700,
            timerRest = 2300
        ),
        Timer(
            timerId = 0,
            timerName = "Workout 3",
            timerSets = 3,
            timerMain = 2500,
            timerRest = 1200
        ),
        Timer(
            timerId = 0,
            timerName = "Workout 4",
            timerSets = 3,
            timerMain = 2500,
            timerRest = 1200
        ),
    )
}