package com.potatomeme.hilt

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.potatomeme.hilt.data.api.LoginRetrofitService
import com.potatomeme.hilt.data.source.UserLocalDataSource
import com.potatomeme.hilt.data.source.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/")
            .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

    @Provides
    fun provideLoginRetrofitService(retrofit: Retrofit): LoginRetrofitService {
        return retrofit.create(LoginRetrofitService::class.java)
    }

    @Provides
    fun provideUserLocalDataSource(application: Application): UserLocalDataSource {
        return UserLocalDataSource(application)
    }

    @Provides
    fun provideUserRemoteDataSource(loginRetrofitService: LoginRetrofitService): UserRemoteDataSource {
        return UserRemoteDataSource(loginRetrofitService)
    }
}