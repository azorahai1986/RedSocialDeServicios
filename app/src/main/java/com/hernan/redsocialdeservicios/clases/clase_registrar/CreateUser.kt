package com.hernan.redsocialdeservicios.clases.clase_registrar

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.login.ProviderType
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.CargarFotoPerfilFragment
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.DatosPersonalesFragment
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.RegistrarUsuarioFragment

class CreateUser {
    lateinit var cargarFoto:CargarFotoPerfilFragment
    lateinit var datosPersonales:DatosPersonalesFragment
    lateinit var registrarUser:RegistrarUsuarioFragment
    //private val getUser = FirebaseAuth.getInstance().currentUser
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    var prvider = ProviderType.GOOGLE
    var error:String = "ERROR"

    fun createUser(email: String, password: String) {

        cargarFoto = CargarFotoPerfilFragment()
        datosPersonales = DatosPersonalesFragment()
        registrarUser = RegistrarUsuarioFragment()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
                if (it.isSuccessful){
                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener {

                        Log.e("IT EK PAYAZO", it.toString())

                    }

                    var emailExitoso = it.result?.user?.email?: ""
                    prvider = ProviderType.GOOGLE
                    var uidDelUsuario = it.result?.user?.uid?: ""
                }


            }

    }
}