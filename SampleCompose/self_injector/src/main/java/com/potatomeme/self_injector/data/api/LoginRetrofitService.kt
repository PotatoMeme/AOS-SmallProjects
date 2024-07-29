package com.potatomeme.self_injector.data.api

import com.potatomeme.self_injector.data.model.NetworkResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginRetrofitService {
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("users/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): NetworkResponse<String>
}