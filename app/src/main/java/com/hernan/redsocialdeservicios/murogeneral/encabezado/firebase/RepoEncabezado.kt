package com.hernan.redsocialdeservicios.murogeneral.encabezado.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos.ModeloEncabezado
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class RepoEncabezado {
    var listener: ListenerRegistration? = null
    suspend fun getEncabezado(): Flow<ResultEncabezado<ModeloEncabezado>> = channelFlow {
        listener = getFirestoreEncabezado().addSnapshotListener { value, error ->
            if (error != null) {
                launch {
                    send(ResultEncabezado.Error(error))
                }
                Log.e("PruebaListError", error.toString())
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {

                val doc = it.document.toObject(ModeloEncabezado::class.java)
                doc.id = it.document.id
                //Log.e("Prueba Encabezado", doc.toString())
                when (it.type) {
                    DocumentChange.Type.ADDED -> doc.type = ModeloEncabezado.TYPE.ADD
                    DocumentChange.Type.MODIFIED -> doc.type = ModeloEncabezado.TYPE.UPDATE
                    DocumentChange.Type.REMOVED -> doc.type = ModeloEncabezado.TYPE.REMOVE
                }
                launch {
                    send(ResultEncabezado.Success(doc))
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