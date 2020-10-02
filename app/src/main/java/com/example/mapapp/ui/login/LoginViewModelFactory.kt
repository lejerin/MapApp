package com.example.mapapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.data.repositories.LoginRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }

}