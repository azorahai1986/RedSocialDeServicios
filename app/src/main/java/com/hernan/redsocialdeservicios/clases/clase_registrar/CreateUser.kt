package com.hernan.redsocialdeservicios.clases.clase_registrar

import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.login.ProviderType
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.CargarFotoPerfilFragment
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.DatosPersonalesFragment

class CreateUser {
    lateinit var cargarFoto:CargarFotoPerfilFragment
    lateinit var datosPersonales:DatosPersonalesFragment

    var prvider = ProviderType.GOOGLE
    var error:String = "ERROR"

    fun createUser(email: String, password: String) {
        cargarFoto = CargarFotoPerfilFragment()
        datosPersonales = DatosPersonalesFragment()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener {
                if (it.isSuccessful){
                    //Log.e("FIREBASE Create", it.result?.user?.uid.toString())

                    var emailExitoso = it.result?.user?.email?: ""
                    prvider = ProviderType.GOOGLE
                    var uidDelUsuario = it.result?.user?.uid?: ""
                }

            }
    }
}