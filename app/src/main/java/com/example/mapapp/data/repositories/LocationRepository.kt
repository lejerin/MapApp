package com.example.mapapp.data.repositories

import com.example.mapapp.data.models.LocalMapData
import com.example.mapapp.data.network.KakaoApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocationRepository (
    private val api: KakaoApi
)  {


    fun getLocationListWithKeyword(query: String, authorization: String) :  Single<LocalMapData> =
        api.getKeywordMap(query, authorization)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}