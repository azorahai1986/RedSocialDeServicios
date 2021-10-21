package com.hernan.redsocialdeservicios.murogeneral.encabezado.firebase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.core.Repo
import com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos.ModeloEncabezado
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EncabezadoViewModel(private val modelRepository:RepoEncabezado = RepoEncabezado()):ViewModel() {
    val encabezado = MutableLiveData<ModeloEncabezado>()
    val error = MutableLiveData<Throwable>()

    fun getEncabezado(){
        viewModelScope.launch {
            modelRepository.getEncabezado().collect {
                when(it){
                    is ResultEncabezado.Success<*> -> encabezado.value = it.data as ModeloEncabezado?
                    is ResultEncabezado.Error -> error.value = it.error
                }
            }
        }
    }
    fun removeListener() {
        modelRepository.removeListener()
    }

}