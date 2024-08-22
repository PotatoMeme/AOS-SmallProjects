package com.potatomeme.data.usecase

import com.potatomeme.data.UserDataStore
import com.potatomeme.domain.usecase.login.GetTokenUseCase
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : GetTokenUseCase {
    override suspend fun invoke(): String? {
        return userDataStore.getToken()
    }
}