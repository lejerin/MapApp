package com.example.mapapp.ui.map

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.R
import com.example.mapapp.base.BaseFragment
import com.example.mapapp.data.repositories.MapRepository
import com.example.mapapp.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : BaseFragment<FragmentMapBinding>() , OnMapReadyCallback {
    override val layoutResourceId: Int
        get() = R.layout.fragment_map

    private lateinit var factory: MapViewModelFactory
    private lateinit var viewModel: MapViewModel

    private val TAG = "TimeFragment"

    private lateinit var mapView: MapView

    private lateinit var locations : List<LatLng>

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


    }

    override fun onMapReady(p0: NaverMap) {

        viewModel.getMapData()
        viewModel.locationData.observe(this, Observer { list ->
                 locations = list

                val markers = mutableListOf<Marker>()
                locations.forEach {
                    markers+=Marker().apply {
                        position = it
                    }
                }

                markers.forEach {
                    it.map = p0
                }

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


}