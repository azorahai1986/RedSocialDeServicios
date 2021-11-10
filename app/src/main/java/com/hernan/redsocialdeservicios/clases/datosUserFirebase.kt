package com.hernan.redsocialdeservicios.clases

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.trabajosdeusuario.fragments.CargarTrabajoFragment

var dbase = FirebaseFirestore.getInstance()
val getUser = FirebaseAuth.getInstance().currentUser
//var arrayData:ArrayList<String> = ArrayList()
var fotoPerfilFirebase:String? = null
var nombrePerfilFirebase:String? = null
var idDocumentFirebase:String? = null
lateinit var cargar: CargarTrabajoFragment
public fun dataFirebase(){
    val uid = getUser?.uid.toString()
    cargar = CargarTrabajoFragment()
    fotoPerfilFirebase = ""

    dbase.collection("DatosDeUsuarios").whereEqualTo("uid", uid).get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                //arrayData.add(document.data["fotoUsuario"].toString())
                    fotoPerfilFirebase = document.data["fotoUsuario"].toString()

                nombrePerfilFirebase = document.data["nombreYapellido"].toString()
                idDocumentFirebase = document.data["nombreYapellido"].toString()



                Log.e("U I D", nombrePerfilFirebase.toString())

            }


        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }
}