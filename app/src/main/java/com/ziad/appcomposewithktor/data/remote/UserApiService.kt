package com.ziad.appcomposewithktor.data.remote

import com.ziad.appcomposewithktor.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class UserApiService @Inject constructor(
    private val client: HttpClient
){

    suspend fun getUsers(): List<User> {
        return client.get("https://jsonplaceholder.typicode.com/users").body()
    }
}