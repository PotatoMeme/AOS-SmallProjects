package com.potatomeme.hilt_architecture.ui.state

data class LoginUiState(
    val id:String,
    val pw:String,
    val userState: UserState = UserState.NONE
)