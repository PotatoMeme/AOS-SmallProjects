package com.potatomeme.hilt.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParam(
    val loginId: String,
    val password: String
)