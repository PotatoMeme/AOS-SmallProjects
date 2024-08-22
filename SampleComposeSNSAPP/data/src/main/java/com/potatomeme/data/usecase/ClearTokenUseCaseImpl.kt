package com.potatomeme.data.usecase

import com.potatomeme.data.UserDataStore
import com.potatomeme.domain.usecase.login.ClearTokenUseCase
import javax.inject.Inject

class ClearTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore,
) : ClearTokenUseCase {
    override suspend fun invoke() {
        userDataStore.clear()
    }
}