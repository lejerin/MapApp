package com.example.mapapp.ui.location

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.models.LocalMapData
import com.example.mapapp.data.repositories.LocationRepository
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.util.Coroutines
import com.example.mapapp.util.getActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LocationViewModel(
    val repository: LocationRepository
) : BaseViewModel() {

    private val TAG = "LocationViewModel"


    var newSearchText = ""

    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH  -> {
                    getLocationList()
                }
                else -> return false
            }
            return true
        }

    }


    private val _locationList = MutableLiveData<LocalMapData>()
    val locationList : LiveData<LocalMapData>
        get() = _locationList

    fun getLocationList(){
        System.out.println("검색 시작" + newSearchText)

        if(newSearchText.isNotEmpty()){

            val disposable = repository.getLocationListWithKeyword(newSearchText ,
                "KakaoAK 7114008c849d19203b186846030bd6ad")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    System.out.println("개수" + it!!.documents.size)
                    _locationList.value = it
                }, {

                })
            addDisposable(disposable)

        }
    }



    fun activityFinish(v: View) {
        val activity = v.context.getActivity()
        activity?.finish()
    }


}