package com.hernan.redsocialdeservicios.murogeneral.clasesmuro

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class RepoMuro {
    var listener: ListenerRegistration? = null
    suspend fun getMuro(): Flow<ResultMuro<ModeloTrabajos>> = channelFlow {
        listener = getFirestoreMuro().addSnapshotListener { value, error ->
            if (error != null) {
                launch {
                    send(ResultMuro.Error(error))
                }
                Log.e("PruebaListError", error.toString())
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {

                val doc = it.document.toObject(ModeloTrabajos::class.java)
                doc.id = it.document.id
               // Log.e("PruebaList", doc.toString())
                when (it.type) {
                    DocumentChange.Type.ADDED -> doc.type = ModeloTrabajos.TYPE.ADD
                    DocumentChange.Type.MODIFIED -> doc.type = ModeloTrabajos.TYPE.UPDATE
                    DocumentChange.Type.REMOVED -> doc.type = ModeloTrabajos.TYPE.REMOVE
                }
                launch {
                    send(ResultMuro.Success(doc))
                }
            }
        }
        awaitClose {
            listener?.remove()
        }
    }//.flowOn(Dispatchers.IO)

    fun removeListener() {
        listener?.remove()
    }

}