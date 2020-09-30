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
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() , View.OnClickListener
    , SignInDialog.SignInClickListener, SignUpDialog.SignUpClickListener{

    private lateinit var viewModel: LoginViewModel
    lateinit var signInDialog : SignInDialog
    lateinit var signUpDialog : SignUpDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        viewModel.loginResponseData.observe(this, Observer {
            Toast.makeText(this, it.ID.toString() , Toast.LENGTH_SHORT).show()
            signInDialog.dismiss()
            goMainActivity()
        })

        viewModel.signUpResponseData.observe(this, Observer {
            Toast.makeText(this, it.ID.toString() , Toast.LENGTH_SHORT).show()
            signInDialog.dismiss()
            goMainActivity()
        })

        btn_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signin -> {

                val fm = supportFragmentManager
                signInDialog = SignInDialog(this)
                signInDialog.show(fm, "sign in")
            }
            R.id.btn_signup -> {

                val fm = supportFragmentManager
                signUpDialog = SignUpDialog(this)
                signUpDialog.show(fm, "sign up")
            }
        }
    }

    override fun onSignIn(id: String, pw: String) {
        viewModel.startSignIn(id, pw)
    }

    override fun onSignUp(id: String, pw: String) {
        viewModel.startSignUp(id, pw)
    }

    private fun goMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



}