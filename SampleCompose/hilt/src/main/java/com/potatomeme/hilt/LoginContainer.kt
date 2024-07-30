package com.potatomeme.hilt

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.potatomeme.hilt.data.repository.UserDataRepository
import com.potatomeme.hilt.ui.viewmodel.LoginViewModel

class LoginContainer(private val appContainer: AppContainer) {

    private val userDataRepository = UserDataRepository(
        localDataSource = appContainer.createUserLocalDataSource(),
        remoteDataSource = appContainer.createUserRemoteDataSource()
    )//싱글톤으로 관리
    fun createLoginViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {
                return LoginViewModel(userDataRepository) as T
            }
        }
    }//해당메서드로 ViewModel을 만들수있음
}