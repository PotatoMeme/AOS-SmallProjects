package com.potatomeme.hilt.ui.state

data class LoginUiState(
    val id:String,
    val pw:String,
    val userState: UserState = UserState.NONE
)