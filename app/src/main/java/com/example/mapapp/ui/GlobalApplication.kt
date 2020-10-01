package com.example.mapapp.ui

import android.app.Application
import android.content.SharedPreferences
import com.example.mapapp.R
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication : Application() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
    }

}