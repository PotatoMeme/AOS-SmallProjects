package com.potatomeme.hilt_sample

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestClassA @Inject constructor() {
    private val uuid = UUID.randomUUID()
    fun printUUID() {
        Log.e("*****", "TestClassA, printUUID: $uuid")
    }

    fun getUUID() : String = uuid.toString()
}

class TestClassB @Inject constructor(testClassA: TestClassA) {
    private val uuid = UUID.randomUUID()
    private val testClassA = testClassA
    fun printUUID() {
        Log.e("*****", "TestClassB, printUUID: $uuid")
        testClassA.printUUID()
    }

    fun getUUID() : String = uuid.toString()
}

