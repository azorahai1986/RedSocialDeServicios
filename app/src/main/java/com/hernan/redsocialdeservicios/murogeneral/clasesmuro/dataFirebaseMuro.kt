package com.hernan.redsocialdeservicios.murogeneral.clases_comentarios

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


private var dbaseTrabajosUsuario = FirebaseFirestore.getInstance()
private var getUser = FirebaseAuth.getInstance().currentUser
public fun dataFirebaseMuro(){
    val uid = com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.getUser?.uid.toString()

    dbaseTrabajosUsuario.collection("TrabajosDelUsusario").whereEqualTo("uid", uid).get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                //arrayData.add(document.data["fotoUsuario"].toString())


            }


        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }
}