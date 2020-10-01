package com.example.mapapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mapapp.R
import com.example.mapapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
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
            FirebaseAuth.getInstance().signOut()
            val loginActivity = Intent(context, LoginActivity::class.java)
            startActivity(loginActivity)
            activity!!.finish()
        }

    }

}