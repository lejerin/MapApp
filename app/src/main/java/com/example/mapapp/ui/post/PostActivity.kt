package com.example.mapapp.ui.post

import android.Manifest
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.data.repositories.PostRepository
import com.example.mapapp.databinding.ActivityPostBinding
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_post.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class PostActivity : BaseActivity<ActivityPostBinding>() , KodeinAware , OnMapReadyCallback{
    override val layoutResourceId: Int
        get() = R.layout.activity_post

    override val kodein by kodein()
    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel

    private val TAG = "PostActivity"

    private val PERMISSION_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var  mLocationSource: FusedLocationSource

    override fun initStartView() {

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("hkyq9q7769")
        mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

    }

    override fun initDataBinding() {

        val repository = PostRepository()
        factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        viewDataBinding.postVM = viewModel

    }

    override fun initAfterBinding() {

        (mapFragment as MapFragment).getMapAsync(this)


    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    override fun onMapReady(p0: NaverMap) {
        p0.locationSource = mLocationSource
        p0.locationTrackingMode = LocationTrackingMode.Follow


        p0.uiSettings.logoGravity = Gravity.RIGHT or Gravity.BOTTOM
        val margin = p0.uiSettings.logoMargin
        p0.uiSettings.setLogoMargin(0, 0, margin[2]+ 40 , margin[3] + 60)

        p0.uiSettings.setAllGesturesEnabled(false)

        p0.uiSettings.isLocationButtonEnabled = true
        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)


    }


}
