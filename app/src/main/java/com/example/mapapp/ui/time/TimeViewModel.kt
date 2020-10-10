package com.example.mapapp.ui.time

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.models.TimeData
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.data.repositories.TimeRepository
import com.example.mapapp.util.CameraUtil
import com.example.mapapp.util.getActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.coroutines.Job
import java.util.*

class TimeViewModel(
    private val repository: TimeRepository
) : BaseViewModel() {

    private lateinit var job: Job


    val timeList : ObservableArrayList<TimeData> = ObservableArrayList()

    fun getTimeList() {

        timeList.add(TimeData("tt", "tt" ,"cc" , Date()))
        timeList.add(TimeData("tt", "t1" ,"cc2" , Date()))
        timeList.add(TimeData("tt", "tt2" ,"c2c" , Date()))
        timeList.add(TimeData("tt", "tt3" ,"c4c" , Date()))

    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}