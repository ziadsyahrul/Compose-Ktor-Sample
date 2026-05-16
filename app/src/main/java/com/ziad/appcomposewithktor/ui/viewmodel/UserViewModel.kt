package com.ziad.appcomposewithktor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziad.appcomposewithktor.data.repository.UserRepository
import com.ziad.appcomposewithktor.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val  repository: UserRepository
): ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow<String?>(null)
    val isError = _isError.asStateFlow()

    init {
        fetchUser()
    }

    // func fetch user
    fun fetchUser() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getUsers().fold(
                onSuccess = {
                    _users.value = it
                    _isLoading.value = false
                },
                onFailure = {
                   _isError.value = it.message
                    _isLoading.value = false
                }
            )
        }
    }
}