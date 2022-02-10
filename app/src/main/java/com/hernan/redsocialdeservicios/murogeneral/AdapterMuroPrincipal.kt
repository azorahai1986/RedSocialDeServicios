package com.hernan.redsocialdeservicios.murogeneral

import android.app.AlertDialog
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
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import kotlinx.android.synthetic.main.dialog_cargar_imagen.view.*

class AdapterMuroPrincipal(var arrayMuro:ArrayList<ModeloTrabajos>, val fragment: FragmentActivity):RecyclerView
.Adapter<AdapterMuroPrincipal.ViewHolderMuro>() {

    inner class ViewHolderMuro(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = ItemMuroPrincipalBinding.bind(itemView)

        fun bind(arraMuro: ModeloTrabajos){
            binding.textItemMuro.text = arraMuro.enunciado
            binding.textComentarios.text = arraMuro.comentario + " comentarios"
            binding.textLikeMuro.text = arraMuro.like + " me gusta"
            binding.textoNombreUser.text = arraMuro.nombreUsuario
            Glide.with(fragment).load(arraMuro.imagenPrincipal).into(binding.imagenItemMuro)
            Glide.with(fragment).load(arraMuro.imagenUsuario).into(binding.imagenUsuarioMuro)
        }
    }
    override fun getItemCount(): Int = arrayMuro.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMuro =
        ViewHolderMuro(LayoutInflater.from(parent.context).inflate(R.layout.item_muro_principal, parent, false))

    override fun onBindViewHolder(holder: ViewHolderMuro, position: Int) {
        val listDatosMuro = arrayMuro[position]


        holder.bind(listDatosMuro)

        var idUs = listDatosMuro.id

        holder.binding.textComentarMura.setOnClickListener { comentar(idUs) }
        holder.binding.imagenItemMuro.setOnClickListener {
            Toast.makeText(fragment.applicationContext, "$idUs", Toast.LENGTH_SHORT).show()
        }

        getIndex(muroPrincipal = listDatosMuro)

    }

    fun comentar(idDocument: String) {
        fragment.supportFragmentManager.beginTransaction().replace(R.id.containerMuroGral,
            comentariosFragment.newInstance(idDocument)).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }

    fun getIndex(muroPrincipal: ModeloTrabajos): Int {
        var index = -1
        arrayMuro.forEachIndexed { i, p ->
            if(muroPrincipal.id == p.id) {
                index = i
                return@forEachIndexed
            }
        }
        return index

    }

    fun dial(){
        val view = LayoutInflater.from(fragment).inflate(R.layout.dialog_cargar_imagen, null)

        val builder =AlertDialog.Builder(fragment).setView(view)
        builder.create()



    }


}