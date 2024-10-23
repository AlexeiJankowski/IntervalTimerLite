package com.hfad.intervaltimerlite.data

data class Timer(
    var timerId: Long = 0L,
    var timerName: String = "",
    var timerSets: Int = 0,
    var timerMain: Long = 0,
    var timerRest: Long = 0,
)