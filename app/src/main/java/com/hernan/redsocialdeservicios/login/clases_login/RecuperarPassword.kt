package com.hernan.redsocialdeservicios.login.clases_login

import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.login.fragments.RestablecerPaswordFragment

class RecuperarPassword {
    lateinit var auth:FirebaseAuth
    lateinit var restablecerFragment: RestablecerPaswordFragment
    fun recoveryPassword(emailVerification: String) {
        //restablecerFragment = RecuperarPassword()
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailVerification)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val correcto = "correcto"
                    restablecerFragment.restablecerContraseña(correcto)
                }

            }.addOnFailureListener {

            }
    }
}