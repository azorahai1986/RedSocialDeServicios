package com.hernan.redsocialdeservicios.clases

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios
import com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.ResultComentarios
import com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.getFirestoreComentarios
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class repo {

        var listener: ListenerRegistration? = null
        suspend fun getComentarios(idDocument: String?): Flow<ResultComentarios<ModeloCmentarios>> = channelFlow {
            listener = getFirestoreComentarios(idDocument).addSnapshotListener { value, error ->
                if (error != null) {
                    launch {
                        send(ResultComentarios.Error(error))
                    }
                    Log.e("PruebaListError", error.toString())
                    return@addSnapshotListener
                }
                value?.documentChanges?.forEach {

                    val doc = it.document.toObject(ModeloCmentarios::class.java)
                    doc.id = it.document.id
                    Log.e("PruebaList", doc.toString())
                    when (it.type) {
                        DocumentChange.Type.ADDED -> doc.type = ModeloCmentarios.TYPE.ADD
                        DocumentChange.Type.MODIFIED -> doc.type = ModeloCmentarios.TYPE.UPDATE
                        DocumentChange.Type.REMOVED -> doc.type = ModeloCmentarios.TYPE.REMOVE
                    }
                    launch {
                        send(ResultComentarios.Success(doc))
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