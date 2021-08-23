package com.hernan.redsocialdeservicios.murogeneral.clases

import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.clases.fotoPerfilFirebase
import com.hernan.redsocialdeservicios.login.fragments.DatosPersonalesFragment
class SubirComentarios {


    fun subirComent(
        nombre: String,
        idDocumento: String,
        fotoEmisor: String,
        comentario: String
    ) {


        var user = mutableMapOf<String, Any>()

        user["idDoc"] = idDocumento
        user["nombreEmisor"] = nombre
        user["comentario"] = comentario
        user["fotoEmisor"] = fotoEmisor


        FirebaseFirestore.getInstance().collection("comentariosLikes")
            .add(user).addOnSuccessListener {

            }


    }
}