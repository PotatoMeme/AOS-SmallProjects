package com.potatomeme.self_injector.ui.state

data class LoginUiState(
    val id:String,
    val pw:String,
    val userState:UserState = UserState.NONE
)