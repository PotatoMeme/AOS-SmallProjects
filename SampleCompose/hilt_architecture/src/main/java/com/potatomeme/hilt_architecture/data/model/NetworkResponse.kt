package com.potatomeme.hilt_architecture.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val result:String,
    val data:T,
    val errorMessage:String
)