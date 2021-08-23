package com.hernan.redsocialdeservicios.murogeneral.clases

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.clases.getUser
import com.hernan.redsocialdeservicios.trabajosdeusuario.CargarTrabajoFragment


private var dbaseTrabajosUsuario = FirebaseFirestore.getInstance()
private var getUser = FirebaseAuth.getInstance().currentUser
var IDUSUARIO:String? = null
public fun dataFirebaseTrabajosUsuario(){
    IDUSUARIO = ""
    val uid = com.hernan.redsocialdeservicios.murogeneral.clases.getUser?.uid.toString()

    dbaseTrabajosUsuario.collection("TrabajosDelUsusario").whereEqualTo("uid", uid).get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                //arrayData.add(document.data["fotoUsuario"].toString())
                    IDUSUARIO = document.data[""].toString()

                Log.e("I D", IDUSUARIO.toString())

            }


        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }
}