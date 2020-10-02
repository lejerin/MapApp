package com.example.mapapp.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.Job

class PostViewModel(
) : ViewModel() {

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