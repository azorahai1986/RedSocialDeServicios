package com.hernan.redsocialdeservicios.murogeneral.clases_comentarios

sealed class ResultComentarios<out R> {
    data class Success<T>(val data: T): ResultComentarios<T>()
    data class Error(val error: Throwable): ResultComentarios<Nothing>()
}