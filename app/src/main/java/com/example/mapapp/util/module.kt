package com.example.mapapp.util

import com.example.mapapp.base.DataModel
import com.example.mapapp.base.DataModelImpl
import com.example.mapapp.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var modelPart = module {
    factory<DataModel> {
        DataModelImpl()
    }
}

var viewModelPart = module {
    viewModel {
        LoginViewModel(get())
    }
}

var module = listOf(modelPart, viewModelPart)