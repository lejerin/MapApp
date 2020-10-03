package com.example.mapapp.data.network

import com.example.mapapp.data.models.LocalMapData
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {

    //home
    @GET("keyword.json")
    fun getKeywordMap(
        @Query("query") query: String,
        @Header("Authorization") Authorization: String
    ): Single<LocalMapData>


    companion object{
        operator fun invoke() : KakaoApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://dapi.kakao.com/v2/local/search/")
                .build()
                .create(KakaoApi::class.java)
        }
    }


}