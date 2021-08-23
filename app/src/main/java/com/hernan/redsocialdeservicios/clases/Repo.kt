package com.hernan.redsocialdeservicios.clases

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.murogeneral.AdapterComentarios
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios
import com.hernan.redsocialdeservicios.murogeneral.comentariosFragment
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos

class repo {
    fun getUserData(idUsuario: String): LiveData<MutableList<ModeloTrabajos>> {
        /*
        crearé una variable para enviar el precio a la clase AplicarPorcentajesPrecios
         */
        Log.e("idRepo", idUsuario.toString())

        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()
        FirebaseFirestore.getInstance().collection("TrabajosDelUsusario").whereEqualTo("id", idUsuario)
            .get().addOnSuccessListener {
                //Log.e("idRepo2", idUsuario.toString())

                val listData = mutableListOf<ModeloTrabajos>()
                //Log.e("datosFirebase", listData.toString())

                for (obtenerFireBase in it.documents){
                    val indument = obtenerFireBase.toObject(ModeloTrabajos::class.java)

                    indument?.id = obtenerFireBase.id
                    if (indument != null)

                        listData.add(indument)
                   // Log.e("datosFirebase", indument.toString())

                }
                mutableData.value = listData

                //Log.e("datosFirebase", listData.toString())

            }.addOnFailureListener {
                Log.e("ErrorMODELO", it.toString())
                //Esto lo hice para probar si llega internet a la app.
            }
        return mutableData
    }

    fun getUserDataMural(): LiveData<MutableList<ModeloTrabajos>> {
        /*
        crearé una variable para enviar el precio a la clase AplicarPorcentajesPrecios
         */

        val mutableData = MutableLiveData<MutableList<ModeloTrabajos>>()
        FirebaseFirestore.getInstance().collection("TrabajosDelUsusario")
            .get().addOnSuccessListener {
                //Log.e("idRepo2", idUsuario.toString())

                val listData = mutableListOf<ModeloTrabajos>()
                //Log.e("datosFirebase", listData.toString())

                for (obtenerFireBase in it.documents){
                    val indument = obtenerFireBase.toObject(ModeloTrabajos::class.java)

                    indument?.id = obtenerFireBase.id
                    if (indument != null)

                        listData.add(indument)
                    // Log.e("datosFirebase", indument.toString())

                }
                mutableData.value = listData

                //Log.e("datosFirebase", listData.toString())

            }.addOnFailureListener {
                Log.e("ErrorMODELO", it.toString())
                //Esto lo hice para probar si llega internet a la app.
            }
        return mutableData
    }
    fun getComentariosData(idUsuario: String): LiveData<MutableList<ModeloCmentarios>> {
        /*
        crearé una variable para enviar el precio a la clase AplicarPorcentajesPrecios
         */
        Log.e("idRepo", idUsuario.toString())

        val mutableData = MutableLiveData<MutableList<ModeloCmentarios>>()
        FirebaseFirestore.getInstance().collection("comentariosLikes").whereEqualTo("idDoc", idUsuario)
            .get().addOnSuccessListener {
                //Log.e("idRepo2", idUsuario.toString())

                val listData = mutableListOf<ModeloCmentarios>()
                //Log.e("datosFirebase", listData.toString())

                for (obtenerFireBase in it.documents){
                    val indument = obtenerFireBase.toObject(ModeloCmentarios::class.java)

                    indument?.id = obtenerFireBase.id
                    if (indument != null)

                        listData.add(indument)
                    // Log.e("datosFirebase", indument.toString())

                }
                mutableData.value = listData

                //Log.e("datosFirebase", listData.toString())

            }.addOnFailureListener {
                Log.e("ErrorMODELO", it.toString())
                //Esto lo hice para probar si llega internet a la app.
            }
        return mutableData
    }


}