package com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrabajosRealizadosViewModel(private val modelRepository: RepoTrabajosRealizados = RepoTrabajosRealizados()): ViewModel() {
    val traRealizado = MutableLiveData<ModeloTrabajos>()
    val error = MutableLiveData<Throwable>()
    val repo = RepoTrabajosRealizados()
    fun getTrabajoRealizado(idUsuario: String?) {
        viewModelScope.launch {
            modelRepository.getTrabRealizados(idUsuario).collect {
                when(it) {
                    is ResultTrabRealizados.Success<*> -> traRealizado.value = (it.data as ModeloTrabajos?)!!
                    is ResultTrabRealizados.Error -> error.value = it.error
                }
            }
        }
    }

    fun removeListener() {
        modelRepository.removeListener()
    }

    fun fetchTrabajos(idUser: String?): LiveData<MutableList<ModeloTrabajos>> {
        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()
        repo.getDataTrabajos(idUser).observeForever {
            mutableData.value = it

        }
        return mutableData

    }
}