package com.hernan.redsocialdeservicios.murogeneral

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ItemMuroPrincipalBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos

class AdapterMuroPrincipal(var arrayMuro:ArrayList<ModeloTrabajos>, val fragment: FragmentActivity):RecyclerView
.Adapter<AdapterMuroPrincipal.ViewHolderMuro>() {

    inner class ViewHolderMuro(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = ItemMuroPrincipalBinding.bind(itemView)
    }
    override fun getItemCount(): Int = arrayMuro.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMuro =
        ViewHolderMuro(LayoutInflater.from(parent.context).inflate(R.layout.item_muro_principal, parent, false))

    override fun onBindViewHolder(holder: ViewHolderMuro, position: Int) {
        val listDatosMuro = arrayMuro[position]

        holder.binding.textItemMuro.text = listDatosMuro.enunciado
        holder.binding.textComentar.text = listDatosMuro.comentario
        holder.binding.textLikeMuro.text = listDatosMuro.like
        Glide.with(fragment).load(listDatosMuro.imagenPrincipal).into(holder.binding.imagenItemMuro)
        Glide.with(fragment).load(listDatosMuro.imagenUsuario).into(holder.binding.imagenUsuarioMuro)
    }


}