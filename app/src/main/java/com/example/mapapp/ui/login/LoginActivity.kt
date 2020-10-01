package com.example.mapapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.ui.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.android.synthetic.main.activity_login.*
import android.content.SharedPreferences




class LoginActivity : AppCompatActivity() , View.OnClickListener
    , SignInDialog.SignInClickListener, SignUpDialog.SignUpClickListener{

    private lateinit var auth: FirebaseAuth

    private lateinit var viewModel: LoginViewModel
    lateinit var signInDialog : SignInDialog
    lateinit var signUpDialog : SignUpDialog

    private lateinit var googleSignInClient : GoogleSignInClient
    private val RC_SIGN_IN = 9001

    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth


        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setAuth(auth)


        //구글 로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //페이스북 로그인
        callbackManager = CallbackManager.Factory.create()

        btn_sign_facebook.setReadPermissions("email", "public_profile")
        btn_sign_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
//                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
//                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
//                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })


        btn_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        btn_sign_google.setOnClickListener(this)
        btn_sign_kakao.setOnClickListener(this)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(this, "자동 로그인", Toast.LENGTH_SHORT).show()
            goMainActivity()
        }
    }

    private fun googleSignIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
             //   Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            //    Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    setLoginPlatform("G")
                    goMainActivity()


                } else {

//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    // ...
//                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                }

            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
     //   Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    setLoginPlatform("F")
                    goMainActivity()

                    // Sign in success, update UI with the signed-in user's information
                    Log.d("qqq", "signInWithCredential:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("dd", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    private fun signInKakao(){
        // 로그인 공통 callback 구성

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

            if (error != null) {

               // Log.e(TAG, "로그인 실패", error)

            }

            else if (token != null) {

              //  Log.i(TAG, "로그인 성공 ${token.accessToken}")
                setLoginPlatform("K")
            goMainActivity()
            }

        }



// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인

        if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {

            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)

        } else {

            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)

        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signin -> {

                val fm = supportFragmentManager
                signInDialog = SignInDialog(this, viewModel)
                signInDialog.show(fm, "sign in")
            }
            R.id.btn_signup -> {

                val fm = supportFragmentManager
                signUpDialog = SignUpDialog(this, viewModel)
                signUpDialog.show(fm, "sign up")
            }
            R.id.btn_sign_google -> {
                googleSignIn()
            }
            R.id.btn_sign_kakao -> {
                signInKakao()
            }
        }
    }

    override fun successSignIn() {
        setLoginPlatform("E")
        goMainActivity()
    }

    override fun successSignUp() {
        setLoginPlatform("E")
        goMainActivity()
    }

    private fun goMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setLoginPlatform(value: String){
        val editor = getSharedPreferences("Pref", MODE_PRIVATE).edit()
        editor.putString("login", value)
        editor.commit()
    }



}