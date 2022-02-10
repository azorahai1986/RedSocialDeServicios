package com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados

import com.google.firebase.firestore.FirebaseFirestore


fun getFirestoreTrabRealizados(idUsuario: String?) = FirebaseFirestore.getInstance()
    .collection("TrabajosDelUsusario").whereEqualTo("id", idUsuario)
