package com.potatomeme.hilt_architecture.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParam(
    val loginId: String,
    val password: String
)