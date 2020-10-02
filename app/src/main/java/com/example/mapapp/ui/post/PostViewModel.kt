package com.example.mapapp.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.data.repositories.PostRepository

import kotlinx.coroutines.Job

class PostViewModel(
    private val repository: PostRepository
) : BaseViewModel() {

    private lateinit var job: Job
//
//    private val _loginData = MutableLiveData<User>()
//    val loginResponseData : LiveData<User>
//        get() = _loginData

    fun sendPost(id: String, password: String){
        if(id.isNotEmpty() && password.isNotEmpty()){
//            job = Coroutines.ioThenMain(
//                { repository.login(id!!, LoginRequest(id!!,password!!)) },
//                {
//                    _loginData.value = it
//                }
//            )

//            _loginData.value = User("happy", "pw")
        }
    }







    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}