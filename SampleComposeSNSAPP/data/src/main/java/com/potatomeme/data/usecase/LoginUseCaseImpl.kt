package com.potatomeme.data.usecase

import com.potatomeme.data.model.LoginParam
import com.potatomeme.data.retrofit.UserService
import com.potatomeme.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userService: UserService,
) : LoginUseCase {
    override suspend fun invoke(id: String, password: String): Result<String> = kotlin.runCatching {
        val loginParam = LoginParam(id, password)
        val requestBody = loginParam.toRequestBody()
        userService.login(
            requestBody = requestBody
        ).data
    }
}