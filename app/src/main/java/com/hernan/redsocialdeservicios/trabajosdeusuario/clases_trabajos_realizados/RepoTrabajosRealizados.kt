package com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import com.matdev.firestoreflowexample.model.firebase.getFirestoreTrabRealizados
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class RepoTrabajosRealizados {
    var listener: ListenerRegistration? = null
    suspend fun getTrabRealizados(idUsuario: String?): Flow<ResultTrabRealizados<ModeloTrabajos>> = channelFlow {
        listener = getFirestoreTrabRealizados(idUsuario).addSnapshotListener { value, error ->
            if (error != null) {
                launch {
                    send(ResultTrabRealizados.Error(error))
                }
                Log.e("PruebaListError", error.toString())
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {

                val doc = it.document.toObject(ModeloTrabajos::class.java)
                doc.id = it.document.id
                //Log.e("PruebaList", doc.toString())

                when (it.type) {
                    DocumentChange.Type.ADDED -> doc.type = ModeloTrabajos.TYPE.ADD
                    DocumentChange.Type.MODIFIED -> doc.type = ModeloTrabajos.TYPE.UPDATE
                    DocumentChange.Type.REMOVED -> doc.type = ModeloTrabajos.TYPE.REMOVE
                }
                launch {
                    send(ResultTrabRealizados.Success(doc))
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