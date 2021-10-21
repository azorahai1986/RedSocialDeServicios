package com.hernan.redsocialdeservicios.murogeneral.clases_comentarios

import com.google.firebase.firestore.FirebaseFirestore


fun getFirestoreComentarios(idDocument: String?) = FirebaseFirestore.getInstance()
    .collection("comentariosLikes").whereEqualTo("idDoc", idDocument)
