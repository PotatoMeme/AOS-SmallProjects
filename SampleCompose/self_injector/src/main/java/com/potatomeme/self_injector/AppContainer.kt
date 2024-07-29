package com.potatomeme.self_injector

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.potatomeme.self_injector.data.api.LoginRetrofitService
import com.potatomeme.self_injector.data.source.UserLocalDataSource
import com.potatomeme.self_injector.data.source.UserRemoteDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainer constructor(private val context: Context) {

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/")
            .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }


    private fun createLoginRetrofitService(): LoginRetrofitService {
        return createRetrofit().create(LoginRetrofitService::class.java)
    }

    fun createUserLocalDataSource(): UserLocalDataSource {
        return UserLocalDataSource(context)
    }

    fun createUserRemoteDataSource(): UserRemoteDataSource {
        return UserRemoteDataSource(createLoginRetrofitService())
    }

    var loginContainer:LoginContainer? = null
}