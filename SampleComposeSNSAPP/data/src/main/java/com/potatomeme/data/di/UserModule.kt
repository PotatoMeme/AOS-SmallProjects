package com.potatomeme.data.di

import com.potatomeme.data.usecase.LoginUseCaseImpl
import com.potatomeme.domain.usecase.login.LoginUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindLoginUseCase(uc:LoginUseCaseImpl) : LoginUseCase
}