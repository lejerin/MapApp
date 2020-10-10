package com.example.mapapp.ui.time

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.base.BaseFragment
import com.example.mapapp.data.repositories.TimeRepository
import com.example.mapapp.databinding.ActivityPostBinding
import com.example.mapapp.databinding.FragmentTimeBinding
import com.example.mapapp.ui.post.CustomViewPhoto
import com.example.mapapp.ui.post.PostViewModel
import com.example.mapapp.ui.post.PostViewModelFactory
import com.naver.maps.map.OnMapReadyCallback
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class TimeFragment : BaseFragment<FragmentTimeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_time


    private lateinit var factory: TimeViewModelFactory
    private lateinit var viewModel: TimeViewModel

    private val TAG = "TimeFragment"

    override fun initStartView() {

    }

    override fun initDataBinding() {

        val repository = TimeRepository()
        factory = TimeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TimeViewModel::class.java)
        viewDataBinding.timeVM = viewModel
        System.out.println("initDataBinding")
    }

    override fun initAfterBinding() {
        System.out.println("initAfterBinding")
        viewModel.getTimeList()
    }

}