package com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos

import com.google.firebase.firestore.Exclude

data class ModeloEncabezado(var id:String = "",
                            var fotoUsuario:String = "",
                            var nombreYapellido:String = "",
                            @Exclude var type: TYPE = TYPE.ADD){
    enum class TYPE {
        ADD, UPDATE, REMOVE
    }

    override fun equals(other: Any?): Boolean {
        return (other as ModeloEncabezado).id == id
    }
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + nombreYapellido.hashCode()
        result = 31 * result + fotoUsuario.hashCode()
        return result
    }
}
