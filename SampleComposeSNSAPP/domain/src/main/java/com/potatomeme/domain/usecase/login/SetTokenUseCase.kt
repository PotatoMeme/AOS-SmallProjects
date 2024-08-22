package com.potatomeme.domain.usecase.login

interface SetTokenUseCase {
    suspend operator fun invoke(token: String)
}