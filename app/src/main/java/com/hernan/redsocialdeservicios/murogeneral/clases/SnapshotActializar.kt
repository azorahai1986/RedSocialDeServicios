package com.hernan.redsocialdeservicios.murogeneral.clases

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios

interface SnapshotActializar {

    fun snap(){
        var arraySnap: MutableList<DocumentSnapshot>
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        arraySnap = ArrayList()
        val docRef = db.collection("comentariosLikes")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                Log.e("SnapShot2 ", snapshot.documents.toString())
               arraySnap = snapshot.documents
                Log.e("SnapShot array ", arraySnap.toString())

            } else {
                Log.e("SnapShot 3 ", "ERROE DATA")
            }
        }
    }
}