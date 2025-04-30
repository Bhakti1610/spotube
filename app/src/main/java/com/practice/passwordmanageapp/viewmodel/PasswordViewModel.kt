package com.practice.passwordmanageapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.passwordmanageapp.data.Password
import com.practice.passwordmanageapp.repository.PasswordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {

    val passwords = repository.getAllPasswords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addPassword(account: String, username: String, rawPassword: String) {
        viewModelScope.launch {
            repository.insert(Password.create(account, username, rawPassword))
        }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            repository.delete(password)
        }
    }

    fun updatePassword(password: Password) {
        viewModelScope.launch {
            repository.update(password)
        }
    }
}

class PasswordViewModelFactory(private val repository: PasswordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            return PasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}