package com.hernan.redsocialdeservicios.clases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos

class MainViewModel: ViewModel() {
    val repo = repo()

    fun fetchUserData(idUsuario: String): LiveData<MutableList<ModeloTrabajos>> {
        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()
        Log.e("IDUSUARIOTRABAJOS2", idUsuario)
        repo.getUserData(idUsuario).observeForever {
            mutableData.value = it

        }
        return mutableData

    }

    fun UserDataMuro(): LiveData<MutableList<ModeloTrabajos>> {
        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()
        repo.getUserDataMural().observeForever {
            mutableData.value = it

        }
        return mutableData

    }


}