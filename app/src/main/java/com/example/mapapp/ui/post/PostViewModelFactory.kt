package com.example.mapapp.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.data.repositories.PostRepository

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }

}