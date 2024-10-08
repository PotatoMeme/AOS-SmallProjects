package com.potatomeme.hilt_architecture.data.repository

import com.potatomeme.hilt_architecture.data.source.UserLocalDataSource
import com.potatomeme.hilt_architecture.data.source.UserRemoteDataSource


class UserDataRepository constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) {
    suspend fun login(id: String, pw: String): Boolean {
        if(isLoggedIn()){
            return true
        }
        val token = remoteDataSource.login(id, pw) ?: return false
        localDataSource.setToken(token)
        return true
    }

    suspend fun isLoggedIn():Boolean{
        return !localDataSource.getToken().isNullOrEmpty()
    }

    suspend fun getCurrentToken():String?{
        return localDataSource.getToken()
    }

    suspend fun logout(){
        localDataSource.clear()
    }

}