package com.example.mapapp.util

import android.app.Application
import android.content.SharedPreferences
import com.example.mapapp.R
import com.example.mapapp.data.repositories.LoginRepository
import com.example.mapapp.ui.login.LoginViewModelFactory
import com.kakao.sdk.common.KakaoSdk
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class GlobalApplication : Application() , KodeinAware {

    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        prefs = getSharedPreferences("Pref", MODE_PRIVATE)

    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GlobalApplication))


    }

}