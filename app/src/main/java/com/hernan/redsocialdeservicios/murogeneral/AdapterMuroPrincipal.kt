package com.hernan.redsocialdeservicios.murogeneral

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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
        holder.binding.textComentarios.text = listDatosMuro.comentario + " comentarios"
        holder.binding.textLikeMuro.text = listDatosMuro.like + " me gusta"
        Glide.with(fragment).load(listDatosMuro.imagenPrincipal).into(holder.binding.imagenItemMuro)
        Glide.with(fragment).load(listDatosMuro.imagenUsuario).into(holder.binding.imagenUsuarioMuro)

        var idUs = listDatosMuro.id

        holder.binding.textComentarMura.setOnClickListener { comentar(idUs) }
        holder.binding.imagenItemMuro.setOnClickListener {
            Toast.makeText(fragment.applicationContext, "$idUs", Toast.LENGTH_SHORT).show()
            Log.e("I D imagen", idUs)
        }
    }

    fun comentar(idDocument: String) {
        fragment.supportFragmentManager.beginTransaction().replace(R.id.containerMuroGral,
            comentariosFragment.newInstance(idDocument)).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }


}