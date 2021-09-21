package com.hernan.redsocialdeservicios.clases.domain

import com.hernan.redsocialdeservicios.clases.data.IRepo
import com.hernan.redsocialdeservicios.clases.repo
import com.hernan.redsocialdeservicios.clases.vo.Resource

class UserCaseImplement(private val repo:IRepo):IUserCase {
    override suspend fun getCode(): Resource<Int> = repo.getCodeRepo()
}