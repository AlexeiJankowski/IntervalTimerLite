package com.hfad.intervaltimerlite

sealed class Screen(val route: String) {
    object TimerListScreen: Screen("timer_list_screen")
    object AddTimerScreen: Screen("add_timer_screen")
    object TimerScreen: Screen("timer")
}