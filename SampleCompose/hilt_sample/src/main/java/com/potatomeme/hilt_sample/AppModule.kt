package com.potatomeme.hilt_sample

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyName():MyName{
        Log.e("******************", "provideMyName 호출", )
        return MyName()
    }
}