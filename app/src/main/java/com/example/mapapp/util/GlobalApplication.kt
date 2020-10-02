package com.example.mapapp.util

import android.app.Application
import android.content.SharedPreferences
import com.example.mapapp.BuildConfig
import com.example.mapapp.R
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GlobalApplication : Application() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        prefs = getSharedPreferences("Pref", MODE_PRIVATE)


        startKoin {
            androidLogger(Level.NONE)
            modules(
                module
            )
        }
    }

}