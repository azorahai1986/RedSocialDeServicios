package com.hernan.redsocialdeservicios.murogeneral.encabezado.firebase

import com.google.firebase.firestore.FirebaseFirestore

fun getFirestoreEncabezado() = FirebaseFirestore.getInstance()
    .collection("DatosDeUsuarios")