package com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ItemEncabezadoBinding
import java.util.ArrayList

class AdapterEncabezado(var arrayEncabezado:ArrayList<ModeloEncabezado>, val context:FragmentActivity)
    :RecyclerView.Adapter<AdapterEncabezado.ViewHolderEncabezado>() {

    inner class ViewHolderEncabezado(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = ItemEncabezadoBinding.bind(itemView)

        fun bind(listaEncabezado: ModeloEncabezado) {
            binding.textEncabezado.text = listaEncabezado.nombreYapellido
            Glide.with(context).load(listaEncabezado.fotoUsuario).into(binding.imagenEncabezado)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEncabezado =
        ViewHolderEncabezado(LayoutInflater.from(parent.context).inflate(R.layout.item_encabezado, parent, false))


    override fun onBindViewHolder(holder: ViewHolderEncabezado, position: Int) {
        val listaEncabezado = arrayEncabezado[position]
        holder.bind(listaEncabezado)
    }

    override fun getItemCount(): Int = arrayEncabezado.size

    fun getIndex(encabezado: ModeloEncabezado): Int {
        var index = -1
        arrayEncabezado.forEachIndexed { i, p ->
            if(encabezado.id == p.id) {
                index = i
                return@forEachIndexed
            }
        }
        return index
    }
}