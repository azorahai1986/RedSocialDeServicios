package com.hernan.redsocialdeservicios.login

import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.login.fragments.DatosPersonalesFragment


class EnviarDatosALaNube {

    lateinit var datosPersonales: DatosPersonalesFragment
    fun cargarDatosPersonales(
        email: String,
        uid: String,
        nombreYApellido:String,
        fotoUsuario:String,
        edad: String,
    ) {


        datosPersonales = DatosPersonalesFragment()
        var user = mutableMapOf<String, Any>()

        user["email"] = email
        user["uid"] = uid
        user["nombreYapellido"] = nombreYApellido
        user["edad"] = edad
        user["fotoUsuario"] = fotoUsuario


        FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
            .add(user).addOnSuccessListener{

            }

    }


}