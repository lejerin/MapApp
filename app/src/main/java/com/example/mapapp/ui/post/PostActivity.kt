package com.example.mapapp.ui.post

import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.databinding.ActivityPostBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_post.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class PostActivity : BaseActivity<ActivityPostBinding>() , KodeinAware {
    override val layoutResourceId: Int
        get() = R.layout.activity_post

    override val kodein by kodein()
    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    private val TAG = "PostActivity"



    override fun initStartView() {

//        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("hkyq9q7769")
//        mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

    }

    override fun initDataBinding() {

        val repository = PostRepository()
        factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        viewDataBinding.postVM = viewModel

    }

    override fun initAfterBinding() {
//        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(R.id.map, it).commit()
//            }
//
//        mapFragment.getMapAsync(this)

    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }


}
