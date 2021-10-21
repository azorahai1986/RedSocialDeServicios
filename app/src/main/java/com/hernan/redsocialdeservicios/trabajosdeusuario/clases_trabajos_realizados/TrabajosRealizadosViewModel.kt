package com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrabajosRealizadosViewModel(private val modelRepository: RepoTrabajosRealizados = RepoTrabajosRealizados()): ViewModel() {
    val traRealizado = MutableLiveData<ModeloTrabajos>()
    val error = MutableLiveData<Throwable>()

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
}