package com.hernan.redsocialdeservicios.murogeneral.clasesmuro

import com.google.firebase.firestore.FirebaseFirestore


fun getFirestoreMuro() = FirebaseFirestore.getInstance()
    .collection("TrabajosDelUsusario")
