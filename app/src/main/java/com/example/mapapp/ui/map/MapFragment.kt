package com.example.mapapp.ui.map

import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseFragment
import com.example.mapapp.data.repositories.MapRepository
import com.example.mapapp.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : BaseFragment<FragmentMapBinding>() , OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_map


    private lateinit var factory: MapViewModelFactory
    private lateinit var viewModel: MapViewModel

    private val TAG = "TimeFragment"

    override fun initStartView() {
        NaverMapSdk.getInstance(context!!).client = NaverMapSdk.NaverCloudPlatformClient("hkyq9q7769")
    }

    override fun initDataBinding() {
        val repository = MapRepository()
        factory = MapViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MapViewModel::class.java)
        viewDataBinding.mapVM = viewModel
    }

    override fun initAfterBinding() {

        val childFragment = childFragmentManager.findFragmentByTag("main_map_fragment") as? MapFragment ?: return
        childFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {


        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = p0

    }


}