package com.hernan.redsocialdeservicios.clases.data

import com.hernan.redsocialdeservicios.clases.vo.Resource

interface IRepo {
    suspend fun getCodeRepo(): Resource<Int>
}