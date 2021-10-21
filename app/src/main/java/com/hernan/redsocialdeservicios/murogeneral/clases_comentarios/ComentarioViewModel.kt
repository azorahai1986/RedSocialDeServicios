package com.hernan.redsocialdeservicios.murogeneral.clases_comentarios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hernan.redsocialdeservicios.clases.repo
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComentarioViewModel(private val modelRepository: repo = repo()): ViewModel() {
    val comentario = MutableLiveData<ModeloCmentarios>()
    val error = MutableLiveData<Throwable>()

    fun getComentarios(idDocument: String?) {
        viewModelScope.launch {
            modelRepository.getComentarios(idDocument).collect {
                when(it) {
                    is ResultComentarios.Success<*> -> comentario.value = (it.data as ModeloCmentarios?)!!
                    is ResultComentarios.Error -> error.value = it.error
                }
            }
        }
    }

    fun removeListener() {
        modelRepository.removeListener()
    }
}