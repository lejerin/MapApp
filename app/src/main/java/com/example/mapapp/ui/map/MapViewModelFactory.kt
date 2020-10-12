package com.example.mapapp.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.data.repositories.MapRepository
import com.example.mapapp.data.repositories.TimeRepository

@Suppress("UNCHECKED_CAST")
class MapViewModelFactory(
    private val repository: MapRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(repository) as T
    }

}