package com.hernan.redsocialdeservicios.trabajosdeusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val repo = repo()

    fun fetchUserData(idUsuario: String): LiveData<MutableList<ModeloTrabajos>> {
        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()

        repo.getUserData(idUsuario).observeForever {
            mutableData.value = it

        }
        return mutableData

    }
}