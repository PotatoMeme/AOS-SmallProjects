package com.potatomeme.domain.usecase.login

interface ClearTokenUseCase {
    suspend operator fun invoke()
}