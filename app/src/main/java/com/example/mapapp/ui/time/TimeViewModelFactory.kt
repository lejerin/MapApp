package com.example.mapapp.ui.time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.data.repositories.TimeRepository

@Suppress("UNCHECKED_CAST")
class TimeViewModelFactory(
    private val repository: TimeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimeViewModel(repository) as T
    }

}