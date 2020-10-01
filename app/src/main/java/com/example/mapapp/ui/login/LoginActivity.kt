package com.example.mapapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() , View.OnClickListener
    , SignInDialog.SignInClickListener, SignUpDialog.SignUpClickListener{

    private lateinit var auth: FirebaseAuth

    private lateinit var viewModel: LoginViewModel
    lateinit var signInDialog : SignInDialog
    lateinit var signUpDialog : SignUpDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setAuth(auth)

        btn_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            goMainActivity()
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
        }
    }

    override fun successSignIn() {
        goMainActivity()
    }

    override fun successSignUp() {
        goMainActivity()
    }

    private fun goMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



}