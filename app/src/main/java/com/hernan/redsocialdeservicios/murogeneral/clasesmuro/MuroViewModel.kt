package com.hernan.redsocialdeservicios.murogeneral.clasesmuro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MuroViewModel(private val modelRepository: RepoMuro = RepoMuro()): ViewModel() {
    val muro = MutableLiveData<ModeloTrabajos>()
    val error = MutableLiveData<Throwable>()

    fun getMuro() {
        viewModelScope.launch {
            modelRepository.getMuro().collect {
                when(it) {
                    is ResultMuro.Success<*> -> muro.value = (it.data as ModeloTrabajos?)!!
                    is ResultMuro.Error -> error.value = it.error
                }
            }
        }
    }

    fun removeListener() {
        modelRepository.removeListener()
    }
}