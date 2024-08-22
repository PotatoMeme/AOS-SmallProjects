package com.potatomeme.data.di

import com.potatomeme.data.usecase.GetTokenUseCaseImpl
import com.potatomeme.data.usecase.LoginUseCaseImpl
import com.potatomeme.data.usecase.SetTokenUseCaseImpl
import com.potatomeme.data.usecase.SignUpUseCaseImpl
import com.potatomeme.domain.usecase.login.GetTokenUseCase
import com.potatomeme.domain.usecase.login.LoginUseCase
import com.potatomeme.domain.usecase.login.SetTokenUseCase
import com.potatomeme.domain.usecase.login.SignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindLoginUseCase(uc: LoginUseCaseImpl): LoginUseCase

    @Binds
    abstract fun bindSignUpUseCase(uc: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindsGetTokenUseCase(uc: GetTokenUseCaseImpl): GetTokenUseCase

    @Binds
    abstract fun bindsSetTokenUseCase(uc: SetTokenUseCaseImpl): SetTokenUseCase
}