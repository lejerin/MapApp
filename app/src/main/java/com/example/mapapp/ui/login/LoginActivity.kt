package com.example.mapapp.ui.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.LoginClient
import kotlinx.android.synthetic.main.activity_login.*
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.databinding.ActivityLoginBinding
import com.example.mapapp.util.startMainActivity
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware

class LoginActivity : BaseActivity<ActivityLoginBinding>() , View.OnClickListener, EmailSignListener, KodeinAware {
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override val kodein by kodein()
    private lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel


    private val TAG = "LoginActivity"


    //파이어베이스 인증 instance
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    //구글 로그인
    private val RC_SIGN_IN = 9001

    //페이스북 로그인
    private lateinit var callbackManager: CallbackManager


    override fun initStartView() {

        initLoginFacebook()
    }


    override fun initDataBinding() {

        val repository = LoginRepository()
        factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        viewModel.authListener = this
        viewDataBinding.loginVM = viewModel

    }

    override fun initAfterBinding() {
        btn_fake_facebook.setOnClickListener(this)
    }

    override fun onStarted() {
        //progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess(method: Int) {
        //  progressbar.visibility = View.GONE

        setLoginPlatform(
            when(method){
                1, 2->  "E"
                3-> "G"
                4-> "F"
                else -> ""
            }
        )

        startMainActivity()

    }

    override fun onFailure(method: Int, message: String) {
        //    progressbar.visibility = View.GONE
        Toast.makeText(this, "Authentication failed.\n${message}", Toast.LENGTH_SHORT).show()
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_SHORT).show()
            startMainActivity()
        }
    }



    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_fake_facebook -> {
                btn_sign_facebook.performClick()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            viewModel.receiveGoogleSignResult(data)
        }else{
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }


    }


    private fun initLoginFacebook(){
        callbackManager = CallbackManager.Factory.create()
        btn_sign_facebook.setReadPermissions("email", "public_profile")
        btn_sign_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                viewModel.facebookHandleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })

    }


    private fun setLoginPlatform(value: String){
        val editor = getSharedPreferences("Pref", MODE_PRIVATE).edit()
        editor.putString("login", value)
        editor.commit()
    }


}