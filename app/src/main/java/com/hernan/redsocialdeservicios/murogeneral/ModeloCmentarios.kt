package com.hernan.redsocialdeservicios.murogeneral

import com.google.firebase.firestore.Exclude

data class ModeloCmentarios(var id:String ="",
                            var comentario:String = "",
                            var uidReceptor:String = "",
                            var nombreEmisor:String = "",
                            var fotoEmisor:String = "",
                            @Exclude var type: TYPE = TYPE.ADD) {

    enum class TYPE {
        ADD, UPDATE, REMOVE
    }

    override fun equals(other: Any?): Boolean {
        return (other as ModeloCmentarios).id == id
    }
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + comentario.hashCode()
        result = 31 * result + nombreEmisor.hashCode()
        return result
    }

}
