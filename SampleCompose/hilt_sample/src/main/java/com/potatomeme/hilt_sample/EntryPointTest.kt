package com.potatomeme.hilt_sample

import android.content.Context
import android.util.Log
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


class Foo @Inject constructor() {}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FooEntryPoint {
    fun getFoo(): Foo
}

class FooManager {
    private val TAG = "EntryPointTest"
    fun doSomething(context: Context) {
        val fooEntryPoint = EntryPoints.get(context, FooEntryPoint::class.java)
        val foo = fooEntryPoint.getFoo()

        Log.e(TAG, "getFoo: $foo", )
    }
}