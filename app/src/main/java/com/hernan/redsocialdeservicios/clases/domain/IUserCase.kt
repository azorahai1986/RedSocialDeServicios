package com.hernan.redsocialdeservicios.clases.domain

import com.hernan.redsocialdeservicios.clases.vo.Resource

interface IUserCase {
    suspend fun getCode():Resource<Int>
}