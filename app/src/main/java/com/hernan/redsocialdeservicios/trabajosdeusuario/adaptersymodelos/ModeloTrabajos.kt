package com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos

import com.google.firebase.database.Exclude
import com.hernan.redsocialdeservicios.murogeneral.ModeloCmentarios

data class ModeloTrabajos(var id:String = "",
                          var enunciado:String = "",
                          var nombreUsuario:String = "",
                          var imagenUsuario:String = "",
                          var imagenPrincipal:String = "",
                          var arrayImagen:ArrayList<String> = arrayListOf(),
                          var comentario:String = "",
                          var like:String = "",
                          @Exclude var type: TYPE = TYPE.ADD
){
    enum class TYPE {
        ADD, UPDATE, REMOVE
    }

    override fun equals(other: Any?): Boolean {
        return (other as ModeloCmentarios).id == id
    }
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + enunciado.hashCode()
        result = 31 * result + imagenPrincipal.hashCode()
        result = 31 * result + imagenUsuario.hashCode()
        result = 31 * result + arrayImagen.hashCode()
        result = 31 * result + comentario.hashCode()
        result = 31 * result + like.hashCode()
        return result
    }
}