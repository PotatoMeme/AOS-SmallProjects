package com.potatomeme.data.usecase

import android.util.Log
import com.potatomeme.data.model.SignUpParam
import com.potatomeme.data.retrofit.UserService
import com.potatomeme.domain.usecase.login.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val userService: UserService,
) : SignUpUseCase {
    override suspend fun invoke(id: String, username: String, password: String): Result<Boolean> =
        kotlin.runCatching {
            val requestBody = SignUpParam(
                loginId = id,
                name = username,
                password = password,
            ).toRequestBody()
            val response = userService.signUp(requestBody = requestBody)
            response.result == "SUCCESS"
        }

}