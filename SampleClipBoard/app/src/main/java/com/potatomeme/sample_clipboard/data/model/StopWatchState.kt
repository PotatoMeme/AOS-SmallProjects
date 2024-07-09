package com.potatomeme.sample_clipboard.data.model

import java.lang.IllegalArgumentException

sealed class StopWatchState {
    data object Stop : StopWatchState()
    data object Play : StopWatchState()
    data object Pause : StopWatchState()
    companion object{
        const val STATE_STOP = 0
        const val STATE_PLAY = 1
        const val STATE_PAUSE = 2

        fun fromInt(value: Int): StopWatchState {
            return when (value) {
                STATE_STOP -> Stop
                STATE_PLAY -> Play
                STATE_PAUSE -> Pause
                else -> throw IllegalArgumentException("Invalid StopWatchState value: $value")
            }
        }

        fun toInt(state: StopWatchState): Int {
            return when (state) {
                Stop -> STATE_STOP
                Play -> STATE_PLAY
                Pause -> STATE_PAUSE
            }
        }
    }
}