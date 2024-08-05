package com.potatomeme.hilt_sample

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @CustomQualifier
    @Provides
    @Singleton
    fun provideMyName1(): MyName {
        Log.e("******************", "provideMyName1 호출")
        return MyName()
    }

    @Provides
    @Singleton
    fun provideMyName2(): MyName {
        Log.e("******************", "provideMyName2 호출")
        return MyName()
    }

    @Named("test")
    @Provides
    @Singleton
    fun provideMyName3(): MyName {
        Log.e("******************", "provideMyName3 호출")
        return MyName()
    }

    @NamedQualifier("jinsu")
    @Provides
    @Singleton
    fun provideMyName4(): MyName {
        Log.e("******************", "provideMyName4 호출")
        return MyName()
    }

    @NamedQualifier("namsu")
    @Provides
    @Singleton
    fun provideMyName5(): MyName {
        Log.e("******************", "provideMyName5 호출")
        return MyName()
    }

    @Provides
    @IntoSet
    fun provideStringA() : String{
        return "TestA"
    }
    @Provides
    @IntoSet
    fun provideStringB() : String{
        return "TestB"
    }
    @Provides
    @ElementsIntoSet
    fun provideStringC() : Set<String>{
        return listOf("TestC","TestD").toSet()
    }
}

@Qualifier
annotation class CustomQualifier

@Qualifier
annotation class NamedQualifier(val name : String)