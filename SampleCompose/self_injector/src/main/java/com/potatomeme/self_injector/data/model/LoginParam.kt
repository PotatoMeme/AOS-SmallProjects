package com.potatomeme.self_injector.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParam(
    val loginId: String,
    val password: String
)