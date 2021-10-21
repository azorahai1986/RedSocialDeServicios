package com.hernan.redsocialdeservicios.murogeneral.encabezado.firebase

import com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.ResultComentarios

sealed class ResultEncabezado<out R> {
    data class Success<T>(val data: T): ResultEncabezado<T>()
    data class Error(val error: Throwable): ResultEncabezado<Nothing>()
}
