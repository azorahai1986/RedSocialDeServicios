package com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ItemTrabajosUsuarioBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.fragments.ImagenesZoomFragment

class AdapterTrabajo(var listTrabajo:ArrayList<ModeloTrabajos>, val fragment:FragmentActivity):
    RecyclerView.Adapter<AdapterTrabajo.ViewHolderTrabajo>() {
    inner class ViewHolderTrabajo(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemTrabajosUsuarioBinding.bind(itemView)


        fun bind(trabajosRealiz: ModeloTrabajos){
            binding.textComentarios.text =trabajosRealiz.comentario
        }
    }


    override fun getItemCount(): Int = listTrabajo.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTrabajo =
        ViewHolderTrabajo(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trabajos_usuario, parent, false))



    override fun onBindViewHolder(holder: ViewHolderTrabajo, position: Int) {
        val trabajosList = listTrabajo[position]



        holder.binding.textItemTrabajo.text = trabajosList.enunciado
        holder.binding.textComentarios.text = trabajosList.comentario
        holder.binding.textLike.text = trabajosList.like + "Me gusta"
        Glide.with(fragment).load(trabajosList.imagenPrincipal).into(holder.binding.imagenItemTrabajo)
        Glide.with(fragment).load(trabajosList.imagenUsuario).into(holder.binding.imagenUsuarioTrabajos)
        var posicion = holder.absoluteAdapterPosition

        holder.binding.imagenItemTrabajo.setOnClickListener { irFragmentZoom(trabajosList.id, posicion) }
        holder.bind(trabajosList)
    }
    fun getIndex(trabajosRealiz: ModeloTrabajos): Int {
        var index = -1
        listTrabajo.forEachIndexed { i, p ->
            if(trabajosRealiz.id == p.id) {

                index = i
                return@forEachIndexed
            }
        }
        return index
    }

    fun irFragmentZoom(id: String, posicion: Int) {
       // Log.e("IMAGENES PAGER adapterrrrr", id.toString())

        val fragZoom = ImagenesZoomFragment()
        fragment.supportFragmentManager.beginTransaction().replace(R.id.trabajosContenedor,
            ImagenesZoomFragment.newInstance(id))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }




}