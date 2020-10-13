package com.example.mapapp.ui.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mapapp.R
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.repositories.LoginRepository
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel(
    private val repository: LoginRepository
) : BaseViewModel() ,  EmailSignInDialog.SignInClickListener, EmailSignUpDialog.SignUpClickListener{

    private val TAG = "LoginViewModel"

    //auth listener
    var authListener: EmailSignListener? = null

    //이메일 회원가입, 로그인을 위한 다이얼로그
    private lateinit var emailSignInDialog: EmailSignInDialog
    private lateinit var emailSignUpDialog: EmailSignUpDialog

    private fun emailSignIn(email: String?, password: String?) {

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
                emailSignInDialog.receiveResult(true, "")
                authListener?.onSuccess(1, it)
            }, {
                //sending a failure callback
                emailSignInDialog.receiveResult(false,
                    "Authentication failed.\n${it.message!!}")
                authListener?.onFailure(1, it.message!!)
            })
        addDisposable(disposable)
    }

    private fun emailSignUp(email: String?, password: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure(2, "Please input all values")
            return
        }
        authListener?.onStarted()
        val disposable = repository.register(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                emailSignUpDialog.receiveResult(true, "")
                authListener?.onSuccess(2, it)
            }, {
                emailSignUpDialog.receiveResult(false,
                    "Authentication failed.\n${it.message!!}")
            })
        addDisposable(disposable)
    }

    private fun signGoogleWithFirebase(idToken: String){
        authListener?.onStarted()
        val disposable = repository.firebaseAuthWithGoogle(idToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess(3, it)
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
                authListener?.onSuccess(4, it)
            }, {
                authListener?.onFailure(4, it.message!!)
            })
        addDisposable(disposable)
    }

    fun receiveGoogleSignResult(data: Intent?) {

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




    fun showSignInDialog(view: View) {
        val fm = (view.context as AppCompatActivity).supportFragmentManager
        emailSignInDialog = EmailSignInDialog(this)
        emailSignInDialog.show(fm, "sign in")
    }

    fun showSignUpDialog(view: View) {
        val fm = (view.context as AppCompatActivity).supportFragmentManager
        emailSignUpDialog = EmailSignUpDialog(this)
        emailSignUpDialog.show(fm, "sign up")
    }

    override fun inputSignInData(email: String, pw: String) {
        emailSignIn(email, pw)
    }

    override fun inputSignUpData(email: String, pw: String) {
        emailSignUp(email, pw)
    }


    private lateinit var googleSignInClient : GoogleSignInClient
    private val RC_SIGN_IN = 9001

    fun googleSignIn(v: View){

        val activity = v.context as AppCompatActivity

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }



     fun kakaoSignIn(v: View){
        // 로그인 공통 callback 구성

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
                authListener?.onFailure(4, error.message.toString())
            }

            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                getKakaoUserId()
            }

        }

        val context = v.context

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    private fun getKakaoUserId(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
                authListener?.onFailure(4, error.message.toString())
            }
            else if (tokenInfo != null) {
                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")

                authListener?.onSuccess(4, tokenInfo.id.toString())
            }
        }
    }
}