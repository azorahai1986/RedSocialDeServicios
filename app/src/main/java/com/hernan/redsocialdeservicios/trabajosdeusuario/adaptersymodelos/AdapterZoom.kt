package com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ItemPagerZoomBinding
import com.hernan.redsocialdeservicios.murogeneral.comentariosFragment

class AdapterZoom(var arrayZoom:ArrayList<ModeloTrabajos>, val fragment: FragmentActivity):RecyclerView
.Adapter<AdapterZoom.ViewHolderZoom>() {

    inner class ViewHolderZoom(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = ItemPagerZoomBinding.bind(itemView)

        fun bind(listZoom: ModeloTrabajos){
            Glide.with(fragment).load(listZoom.arrayImagen).into(binding.imagenPagerZoom)

            // Log.e("ID ADAPTER", listZoom.id)

        }
    }
    override fun getItemCount(): Int = arrayZoom.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderZoom =
        ViewHolderZoom(LayoutInflater.from(parent.context).inflate(R.layout.item_pager_zoom, parent, false))

    override fun onBindViewHolder(holder: ViewHolderZoom, position: Int) {
        val listZoom = arrayZoom[position]

        holder.bind(listZoom)
        //Glide.with(fragment).load(listZoom).into(holder.binding.imagenPagerZoom)


    }
    fun getIndex(zoomIndex: ModeloTrabajos): Int {
        var index = -1
        arrayZoom.forEachIndexed { i, p ->
            if(zoomIndex.id == p.id) {
                index = i
                return@forEachIndexed
            }
        }
        return index
    }

    /*fun comentar(idDocument: String) {
        fragment.supportFragmentManager.beginTransaction().replace(R.id.containerMuroGral,
            comentariosFragment.newInstance(idDocument)).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }*/



}