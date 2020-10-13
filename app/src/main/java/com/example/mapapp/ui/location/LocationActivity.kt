package com.example.mapapp.ui.location

import android.Manifest
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapapp.R
import com.example.mapapp.base.BaseActivity
import com.example.mapapp.data.models.LocalMapData
import com.example.mapapp.data.network.KakaoApi
import com.example.mapapp.data.repositories.LocationRepository
import com.example.mapapp.databinding.ActivityLocationBinding
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_location.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class LocationActivity : BaseActivity<ActivityLocationBinding>() , KodeinAware, OnMapReadyCallback,
            MapListAdapter.OnItemClickListener{
    override val layoutResourceId: Int
        get() = R.layout.activity_location

    override val kodein by kodein()
    private lateinit var factory: LocationViewModelFactory
    private lateinit var viewModel: LocationViewModel

    private val PERMISSION_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var  mLocationSource: FusedLocationSource

    val mapList = mutableListOf<LocalMapData.Document>()

    private val TAG = "LocationActivity"
    override fun initStartView() {

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_map_client_id))
        mLocationSource = FusedLocationSource(this, PERMISSION_REQUEST_CODE)

        val adapter = MapListAdapter(mapList)
        adapter.setItemClickListener(this)
        viewDataBinding.listView.adapter = adapter
        viewDataBinding.listView.layoutManager = LinearLayoutManager(this)
        viewDataBinding.listView.addItemDecoration(DividerItemDecoration(this@LocationActivity, LinearLayoutManager.VERTICAL))


    }

    override fun initDataBinding() {

        val api = KakaoApi()
        val repository = LocationRepository(api)
        factory = LocationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LocationViewModel::class.java)
        viewDataBinding.locationVM = viewModel

        viewModel.locationList.observe(this, Observer { list ->

            mapList.clear()
            mapList.addAll(list.documents)
            viewDataBinding.listView.adapter!!.notifyDataSetChanged()
            viewDataBinding.slidingBar.panelState = SlidingUpPanelLayout.PanelState.EXPANDED


        })


    }

    override fun initAfterBinding() {

        (map_location as MapFragment).getMapAsync(this)

        viewDataBinding.slidingBar.panelState = SlidingUpPanelLayout.PanelState.HIDDEN


    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.locationSource = mLocationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow


        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
    }

    override fun onClick(v: View, position: Int) {

        val item = mapList[position]
        viewDataBinding.selectTitleText.text = item.place_name
        viewDataBinding.selectLocationBtn.visibility = View.VISIBLE
       // mapViewChange(item.x.toDouble(),item.y.toDouble(),item.placeName)

    }

}