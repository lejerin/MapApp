package com.example.mapapp.ui.setting

import android.content.ContentValues.TAG
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mapapp.R
import com.example.mapapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_logout.setOnClickListener {
            logOut()

        }

    }


    private fun logOut(){
        val prefs = context?.getSharedPreferences("Pref", MODE_PRIVATE)

        when(prefs?.getString("login", "E")){
            "E" -> {
                FirebaseAuth.getInstance().signOut()
            }
            "K" -> {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    }
                    else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    }
                }
            }
            "G" -> {
                Firebase.auth.signOut()
            }
            "F" -> {
                Firebase.auth.signOut()
            }
        }
        val loginActivity = Intent(context, LoginActivity::class.java)
        startActivity(loginActivity)
        activity!!.finish()

    }

}