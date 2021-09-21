package com.hernan.redsocialdeservicios.clases.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hernan.redsocialdeservicios.clases.domain.IUserCase

class ViewMoselFactory(private val useCase:IUserCase):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IUserCase::class.java).newInstance(useCase)
    }
}