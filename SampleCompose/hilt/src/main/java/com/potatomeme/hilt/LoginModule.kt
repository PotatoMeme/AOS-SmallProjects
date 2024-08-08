package com.potatomeme.hilt

import com.potatomeme.hilt.data.repository.UserDataRepository
import com.potatomeme.hilt.data.source.UserLocalDataSource
import com.potatomeme.hilt.data.source.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginModule {
    @Provides
    @ActivityRetainedScoped
    fun provideUserDataRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource,
    ): UserDataRepository {
        return UserDataRepository(
            localDataSource = userLocalDataSource,
            remoteDataSource = userRemoteDataSource
        )
    }

    /*@Provides
    fun provideLoginViewModelFactory(userDataRepository: UserDataRepository): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle,
            ): T {
                return LoginViewModel(userDataRepository) as T
            }
        }
    }*/
}