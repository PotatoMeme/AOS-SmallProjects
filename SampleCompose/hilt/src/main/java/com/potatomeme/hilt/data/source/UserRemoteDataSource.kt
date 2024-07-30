package com.potatomeme.hilt.data.source

import com.potatomeme.hilt.data.model.LoginParam
import com.potatomeme.hilt.data.api.LoginRetrofitService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody


class UserRemoteDataSource constructor(
    private val service: LoginRetrofitService
) {
    suspend fun login(id:String, pw:String):String?{
        return try {
            val param = Json.encodeToString(LoginParam(id, pw))
            service.login(param.toRequestBody()).data
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}