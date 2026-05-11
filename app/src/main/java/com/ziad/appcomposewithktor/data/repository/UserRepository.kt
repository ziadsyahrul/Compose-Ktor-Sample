package com.ziad.appcomposewithktor.data.repository

import com.ziad.appcomposewithktor.data.remote.UserApiService
import com.ziad.appcomposewithktor.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: UserApiService
) {

    suspend fun getUsers(): Result<List<User>> = runCatching {
        apiService.getUsers()
    }
}