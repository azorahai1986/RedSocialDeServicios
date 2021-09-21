package com.hernan.redsocialdeservicios.clases.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hernan.redsocialdeservicios.clases.vo.Resource
import kotlinx.coroutines.tasks.await

class RepoImplement:IRepo {
    override suspend fun getCodeRepo(): Resource<Int> {
        // datos de firerbase
        val resultData = FirebaseFirestore.getInstance().collection("Numero")
            .document("num").get().await()
        val obtenerValor = resultData.getLong("n")
        return Resource.Success(obtenerValor!!.toInt())
    }
}