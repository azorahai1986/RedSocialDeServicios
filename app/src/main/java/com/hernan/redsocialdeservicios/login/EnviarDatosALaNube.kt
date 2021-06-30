package com.hernan.redsocialdeservicios.login

import com.google.firebase.firestore.FirebaseFirestore


class EnviarDatosALaNube {

    lateinit var datosPersonales:DatosPersonalesFragment
    fun cargarDatosPersonales(
        email: String,
        uid: String,
        nombre: String,
        apellido: String,
        edad: String,
        nacimiento: String
    ) {

        datosPersonales = DatosPersonalesFragment()
        var exito= "Exito"
        var user = mutableMapOf<String, Any>()

        user["email"] = email
        user["uid"] = uid
        user["nombre"] = nombre
        user["apellido"] = apellido
        user["edad"] = edad
        user["nacimiento"] = nacimiento


        FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
            .add(user).addOnSuccessListener{

            }

    }
}