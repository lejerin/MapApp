package com.example.mapapp.ui.login

/*
method
1 -> email sign in
2 -> email sign up
3 -> google sign
4 -> facebook sign
5 -> kakao sign

 */
interface EmailSignListener {

    fun onStarted()
    fun onSuccess(method: Int)
    fun onFailure(method: Int, message: String)
}