package com.example.mapapp.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.data.repositories.LocationRepository


@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory(
    val repository: LocationRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationViewModel(repository) as T
    }

}