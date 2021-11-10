package com.hernan.redsocialdeservicios.login

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.CargarFotoPerfilFragment


class EnviarDatosALaNube {

    lateinit var carfoto:CargarFotoPerfilFragment
    fun cargarDatosPersonales(
        email: String,
        nombreYApellido: String,
        edad: String,
        uidRecuperado: String,
        fotoUsuario: String
    ) {


        carfoto = CargarFotoPerfilFragment()
        var user = mutableMapOf<String, Any>()

        user["email"] = email
        user["nombreYapellido"] = nombreYApellido
        user["edad"] = edad
        user["uid"] = uidRecuperado
        user["fotoUsuario"] = fotoUsuario


        FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
            .add(user).addOnSuccessListener{
                Log.e("FIREBASE AUTH", it.id)
                val idEnviar = it.id
                //carfoto.irAActivityMuro(idEnviar)


            }

    }


}