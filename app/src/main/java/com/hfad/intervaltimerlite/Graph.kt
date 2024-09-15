package com.hfad.intervaltimerlite

import android.content.Context
import androidx.room.Room
import com.hfad.intervaltimerlite.data.TimerDatabase
import com.hfad.intervaltimerlite.data.TimerRepository

object Graph {
    lateinit var database: TimerDatabase

    val timerRepository by lazy {
        TimerRepository(timerDao = database.timerDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, TimerDatabase::class.java, "timerlist.db").build()
    }
}