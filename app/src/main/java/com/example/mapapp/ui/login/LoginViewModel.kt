package com.example.mapapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.base.DataModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val model: DataModel) : BaseViewModel() {

    private val TAG = "LoginViewModel"


    private lateinit var auth: FirebaseAuth


    fun setAuth(auth: FirebaseAuth){
        this.auth = auth
    }


    private val _loginData = MutableLiveData<Task<AuthResult>>()
    val loginResponseData : LiveData<Task<AuthResult>>
        get() = _loginData

    fun startSignIn(email: String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()){

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    _loginData.value = task
                }

        }


    }

    private val _signUpData = MutableLiveData<Task<AuthResult>>()
    val signUpResponseData : LiveData<Task<AuthResult>>
        get() = _signUpData

    fun startSignUp(email: String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    _signUpData.value = task
                }
        }
    }




}