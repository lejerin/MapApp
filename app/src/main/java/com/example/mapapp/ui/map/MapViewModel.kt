package com.example.mapapp.ui.map

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.models.DateYM
import com.example.mapapp.data.models.TimeData
import com.example.mapapp.data.repositories.MapRepository
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.data.repositories.TimeRepository
import com.example.mapapp.util.CameraUtil
import com.example.mapapp.util.Coroutines
import com.example.mapapp.util.getActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*

class MapViewModel(
    private val repository: MapRepository
) : BaseViewModel() {

    private lateinit var job: Job


        private val _locationData = MutableLiveData<List<LatLng>>()
        val locationData : LiveData<List<LatLng>>
        get() = _locationData

    fun getMapData(){
        if(true){
            job = Coroutines.ioThenMain(
                { },
                {
                    _locationData.value = getMapData2()
                }
            )
        }
    }


    private fun getMapData2(): MutableList<LatLng> {
        val locations = mutableListOf<LatLng>()
        locations.add(LatLng(37.5670135, 126.9783740))
        locations.add(LatLng(37.5610135, 126.9713740))
        locations.add(LatLng(37.5680135, 126.9789740))
        locations.add(LatLng(37.5670235, 126.9733740))

        return locations
    }


    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}