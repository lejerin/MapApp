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

class LoginActivity : BaseActivity<ActivityLoginBinding>() , View.OnClickListener
    , EmailSignInDialog.SignInClickListener, EmailSignUpDialog.SignUpClickListener, EmailSignListener, KodeinAware {
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override val kodein by kodein()
    private lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel


    private val TAG = "LoginActivity"

    //이메일 회원가입, 로그인을 위한 다이얼로그
    lateinit var emailSignInDialog: EmailSignInDialog
    lateinit var emailSignUpDialog: EmailSignUpDialog

    //파이어베이스 인증 instance
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    //구글 로그인
    private lateinit var googleSignInClient : GoogleSignInClient
    private val RC_SIGN_IN = 9001

    //페이스북 로그인
    private lateinit var callbackManager: CallbackManager


    override fun initStartView() {

        initLoginGoogle()
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
        btn_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        btn_fake_facebook.setOnClickListener(this)
        btn_fake_google.setOnClickListener(this)
        btn_sign_kakao.setOnClickListener(this)
    }

    override fun onStarted() {
        //progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess(method: Int) {
        //  progressbar.visibility = View.GONE

        when(method){
            1-> {
                setLoginPlatform("E")
                emailSignInDialog.receiveResult(true, "")
            }
            2-> {
                setLoginPlatform("E")
                emailSignUpDialog.receiveResult(true, "")
            }
            3-> {
                setLoginPlatform("G")
            }
            4-> {
                setLoginPlatform("F")
            }
        }
        startMainActivity()


    }

    override fun onFailure(method: Int, message: String) {
        //    progressbar.visibility = View.GONE
        when(method){
            1-> {
                emailSignInDialog.receiveResult(false,
                    "Authentication failed.\n${message}")
            }
            2-> {
                emailSignUpDialog.receiveResult(false,
                    "Authentication failed.\n${message}")
            }
            else-> {
                Toast.makeText(this, "Authentication failed.\n${message}", Toast.LENGTH_SHORT).show()
            }
        }

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
            R.id.btn_signin -> {

                val fm = supportFragmentManager
                emailSignInDialog = EmailSignInDialog(this)
                emailSignInDialog.show(fm, "sign in")
            }
            R.id.btn_signup -> {

                val fm = supportFragmentManager
                emailSignUpDialog = EmailSignUpDialog(this)
                emailSignUpDialog.show(fm, "sign up")
            }
            R.id.btn_fake_google -> {
                googleSignIn()
            }
            R.id.btn_fake_facebook -> {
                btn_sign_facebook.performClick()
            }
            R.id.btn_sign_kakao -> {
                signInKakao()
            }
        }
    }

    override fun inputSignInData(email: String, pw: String) {
        viewModel.emailSignIn(email, pw)
    }

    override fun inputSignUpData(email: String, pw: String) {
        viewModel.emailSignUp(email, pw)
    }


    private fun initLoginGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
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



    private fun signInKakao(){
        // 로그인 공통 callback 구성

        val callback = viewModel.kakaoCallback

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }



    private fun setLoginPlatform(value: String){
        val editor = getSharedPreferences("Pref", MODE_PRIVATE).edit()
        editor.putString("login", value)
        editor.commit()
    }


}