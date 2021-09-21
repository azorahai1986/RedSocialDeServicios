package com.hernan.redsocialdeservicios.clases.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hernan.redsocialdeservicios.clases.domain.IUserCase
import com.hernan.redsocialdeservicios.clases.vo.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MViemodel(useCase:IUserCase):ViewModel() {

    // aca recuperaré los datos de firabase. el livedata es una extension que observa los datos para aplicarlos en la vista()

    val fetcVersion = liveData(Dispatchers.IO) {

        emit(Resource.Loading())
        try {
            val version = useCase.getCode()
            emit(version)

        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}