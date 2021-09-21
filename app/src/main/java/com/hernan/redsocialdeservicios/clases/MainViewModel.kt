package com.hernan.redsocialdeservicios.clases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun userDataComentarios(idUsuario: String): LiveData<MutableList<ModeloCmentarios>> {
        GlobalScope.launch(Dispatchers.IO) {
            delay(5000L)
        }
        val mutableData = MutableLiveData<MutableList<ModeloCmentarios>>()
        repo.getComentariosData(idUsuario).observeForever {


            mutableData.value = it

        }
        return mutableData

    }



}