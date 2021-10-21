package com.hernan.redsocialdeservicios.murogeneral.clases_comentarios

import com.google.firebase.firestore.FirebaseFirestore

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