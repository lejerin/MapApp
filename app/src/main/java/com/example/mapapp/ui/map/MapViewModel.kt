package com.example.mapapp.ui.map

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.example.mapapp.base.BaseViewModel
import com.example.mapapp.data.models.DateYM
import com.example.mapapp.data.models.TimeData
import com.example.mapapp.data.repositories.MapRepository
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.data.repositories.TimeRepository
import com.example.mapapp.util.CameraUtil
import com.example.mapapp.util.getActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*

class MapViewModel(
    private val repository: MapRepository
) : BaseViewModel() {

    private lateinit var job: Job



    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}