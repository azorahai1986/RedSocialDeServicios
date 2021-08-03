package com.hernan.redsocialdeservicios.trabajosdeusuario

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hernan.redsocialdeservicios.R
import kotlinx.android.synthetic.main.item_imagenescrear.view.*

class AdapterViewPagerTrabajos(var arrayImagenes: ArrayList<Uri>): RecyclerView.Adapter<AdapterViewPagerTrabajos.ImagenesViewHolder>() {

        inner class ImagenesViewHolder(itemview: View): RecyclerView.ViewHolder(itemview)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewPagerTrabajos.ImagenesViewHolder =
            ImagenesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_imagenescrear, parent, false))

        override fun getItemCount() = arrayImagenes.size

        override fun onBindViewHolder(holder: ImagenesViewHolder, position: Int) {
            var imagenes = arrayImagenes[position]
            Log.e("imagenadapter", imagenes.toString())

            holder.itemView.imagenesPagerTrabajo.setImageURI(imagenes)

        }
}