package com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados

sealed class ResultTrabRealizados<out R> {
    data class Success<T>(val data: T): ResultTrabRealizados<T>()
    data class Error(val error: Throwable): ResultTrabRealizados<Nothing>()
}