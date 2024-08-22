package com.potatomeme.data.usecase

import com.potatomeme.data.UserDataStore
import com.potatomeme.domain.usecase.login.GetTokenUseCase
import com.potatomeme.domain.usecase.login.SetTokenUseCase
import javax.inject.Inject

class SetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : SetTokenUseCase {
    override suspend fun invoke(token: String) {
        userDataStore.setToken(token)
    }

}