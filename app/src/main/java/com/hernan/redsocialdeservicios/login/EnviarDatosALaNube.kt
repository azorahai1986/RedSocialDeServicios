package com.hernan.redsocialdeservicios.login

import com.google.firebase.firestore.FirebaseFirestore


class EnviarDatosALaNube {

    lateinit var datosPersonales:DatosPersonalesFragment
    fun cargarDatosPersonales(
        email: String,
        uid: String,
        nombreYApellido:String,
        nombre: String,
        apellido:String,
        fotoUsuario:String,
        edad: String,
    ) {


        datosPersonales = DatosPersonalesFragment()
        var user = mutableMapOf<String, Any>()

        user["email"] = email
        user["uid"] = uid
        user["nombre y apellido"] = nombreYApellido
        user["nombre"] = nombre
        user["apellido"] = apellido
        user["edad"] = edad
        user["fotoUsuario"] = fotoUsuario


        FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
            .add(user).addOnSuccessListener{

            }

    }


}