package com.hernan.redsocialdeservicios

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private var emailRecibido: String? = null
private var nombreRecibido: String? = null
private var fotoRecibida: String? = null
private var authRecibido: String? = null
private lateinit var auth: FirebaseAuth

fun onbtenerUser(){
    auth = Firebase.auth
    val user = FirebaseAuth.getInstance().currentUser
    user?.let {
        // Name, email address, and profile photo Url
        emailRecibido = user.email
        nombreRecibido = user.displayName
        authRecibido = user.uid
        fotoRecibida = user.photoUrl?.toString()

    }
}