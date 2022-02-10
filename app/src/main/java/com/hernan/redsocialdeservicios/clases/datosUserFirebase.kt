package com.hernan.redsocialdeservicios.clases

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.CargarFotoPerfilFragment
import com.hernan.redsocialdeservicios.trabajosdeusuario.fragments.CargarTrabajoFragment

lateinit var dbase :FirebaseFirestore
val getUser = FirebaseAuth.getInstance().currentUser
//var arrayData:ArrayList<String> = ArrayList()
var fotoPerfilFirebase:String? = null
var nombrePerfilFirebase:String? = null
//var idDocumentFirebase:String? = null
lateinit var cargar: CargarTrabajoFragment
lateinit var fotoPerfil: CargarFotoPerfilFragment
public fun dataFirebase(){
    val uid = getUser?.uid.toString()
    cargar = CargarTrabajoFragment()
    fotoPerfil = CargarFotoPerfilFragment()
    dbase = FirebaseFirestore.getInstance()
    var idDocumentFirebase:String
    fotoPerfilFirebase = ""

    dbase.collection("DatosDeUsuarios").whereEqualTo("uid", uid).get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                //arrayData.add(document.data["fotoUsuario"].toString())
                    fotoPerfilFirebase = document.data["fotoUsuario"].toString()

                nombrePerfilFirebase = document.data["nombreYapellido"].toString()
                idDocumentFirebase = document.id

                Log.e("U I D", idDocumentFirebase.toString())

               //fotoPerfil.idDocument(idDocumentFirebase!!)


            }



        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }
}