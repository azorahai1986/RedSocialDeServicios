package com.hernan.redsocialdeservicios.murogeneral.clasesmuro

sealed class ResultMuro<out R> {
    data class Success<T>(val data: T): ResultMuro<T>()
    data class Error(val error: Throwable): ResultMuro<Nothing>()
}