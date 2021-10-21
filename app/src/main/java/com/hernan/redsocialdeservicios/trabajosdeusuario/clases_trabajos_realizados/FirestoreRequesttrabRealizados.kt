package com.matdev.firestoreflowexample.model.firebase

import com.google.firebase.firestore.FirebaseFirestore


fun getFirestoreTrabRealizados(idUsuario: String?) = FirebaseFirestore.getInstance()
    .collection("TrabajosDelUsusario").whereEqualTo("id", idUsuario)
