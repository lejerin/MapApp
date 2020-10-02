package com.example.mapapp.ui.login

import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.util.startMainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginViewModel(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val TAG = "LoginViewModel"


    //auth listener
    var authListener: EmailSignListener? = null


    fun emailSignIn(email: String?, password: String?) {

        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(1, "Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess(1)
            }, {
                //sending a failure callback
                authListener?.onFailure(1, it.message!!)
            })
        addDisposable(disposable)
    }

    fun emailSignUp(email: String?, password: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(2, "Please input all values")
            return
        }
        authListener?.onStarted()
        val disposable = repository.register(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess(2)
            }, {
                authListener?.onFailure(2, it.message!!)
            })
        addDisposable(disposable)
    }

    private fun signGoogleWithFirebase(idToken: String){
        authListener?.onStarted()
        val disposable = repository.firebaseAuthWithGoogle(idToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess(3)
            }, {
                authListener?.onFailure(3, it.message!!)
            })
        addDisposable(disposable)
    }

    fun facebookHandleFacebookAccessToken(token: AccessToken){
        authListener?.onStarted()
        val disposable = repository.facebookHandleFacebookAccessToken(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess(4)
            }, {
                authListener?.onFailure(4, it.message!!)
            })
        addDisposable(disposable)
    }

    fun receiveGoogleSignResult(
        data: Intent?
    ) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                signGoogleWithFirebase(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                authListener?.onFailure(3, e.toString())
            }
    }


    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
            authListener?.onFailure(4, error.message.toString())
        }

        else if (token != null) {

            Log.i(TAG, "로그인 성공 ${token.accessToken}")
            authListener?.onSuccess(4)
        }

    }



}