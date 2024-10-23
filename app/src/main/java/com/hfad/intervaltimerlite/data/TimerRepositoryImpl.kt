package com.hfad.intervaltimerlite.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TimerRepositoryImpl(
    private val timerDao: TimerDao,
    private val dispatcher: CoroutineDispatcher
): TimerRepository {
    override suspend fun addTimer(timer: Timer) {
        withContext(dispatcher) {
            timerDao.addTimer(
                TimerEntity(
                    timerId = timer.timerId,
                    timerName = timer.timerName,
                    timerSets = timer.timerSets,
                    timerMain = timer.timerMain,
                    timerRest = timer.timerRest
                )
            )
        }

    }

    override suspend fun getAllTimers(): Flow<List<Timer>> {
        return withContext(dispatcher) {
            timerDao.getAllTimers()
                .map { timers ->
                    timers.map { timerEntity ->
                        Timer(
                            timerId = timerEntity.timerId,
                            timerName = timerEntity.timerName,
                            timerSets = timerEntity.timerSets,
                            timerMain = timerEntity.timerMain,
                            timerRest = timerEntity.timerRest
                        )
                    }
                }
        }
    }

    override suspend fun getTimerById(timerId: Long): Flow<Timer> {
        return withContext(dispatcher) {
            timerDao.getTimerById(timerId)
                .map { timerEntity ->
                    Timer(
                        timerId = timerEntity.timerId,
                        timerName = timerEntity.timerName,
                        timerSets = timerEntity.timerSets,
                        timerMain = timerEntity.timerMain,
                        timerRest = timerEntity.timerRest
                    )
                }
        }
    }

    override suspend fun updateTimer(timer: Timer) {
        withContext(dispatcher) {
            timerDao.updateTimer(
                TimerEntity(
                    timerId = timer.timerId,
                    timerName = timer.timerName,
                    timerSets = timer.timerSets,
                    timerMain = timer.timerMain,
                    timerRest = timer.timerRest
                )
            )
        }
    }

    override suspend fun deleteTimer(timer: Timer) {
        withContext(dispatcher) {
            timerDao.deleteTimer(
                TimerEntity(
                    timerId = timer.timerId,
                    timerName = timer.timerName,
                    timerSets = timer.timerSets,
                    timerMain = timer.timerMain,
                    timerRest = timer.timerRest
                )
            )
        }
    }
}