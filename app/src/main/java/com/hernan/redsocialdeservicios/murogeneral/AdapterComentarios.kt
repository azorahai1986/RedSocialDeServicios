package com.hernan.redsocialdeservicios.murogeneral

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ItemComentariosBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos

class AdapterComentarios(var arrayComentarios:ArrayList<ModeloCmentarios>, val fragment:FragmentActivity):
    RecyclerView.Adapter<AdapterComentarios.ViewHolderCom>() {

    inner class ViewHolderCom(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemComentariosBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCom =
        ViewHolderCom(LayoutInflater.from(parent.context).inflate(R.layout.item_comentarios, parent, false))

    override fun onBindViewHolder(holder: ViewHolderCom, position: Int) {
        val arrayComent = arrayComentarios[position]

        holder.binding.textNombreUserComent.text = arrayComent.nombreEmisor
        holder.binding.textComentarioComent.text = arrayComent.comentario
        Glide.with(fragment).load(arrayComent.fotoEmisor).into(holder.binding.imagUsuarioComent)



    }

    override fun getItemCount(): Int = arrayComentarios.size


}

