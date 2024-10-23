package com.hfad.intervaltimerlite.di

import androidx.room.Room
import com.hfad.intervaltimerlite.TimerViewModel
import com.hfad.intervaltimerlite.data.TimerDatabase
import com.hfad.intervaltimerlite.data.TimerRepository
import com.hfad.intervaltimerlite.data.TimerRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModules = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<TimerRepository> { TimerRepositoryImpl(get(), get()) }
    single { TimerViewModel(get()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            TimerDatabase::class.java,
            "timer-database"
        ).build()
    }
    single { get<TimerDatabase>().timerDao() }
}