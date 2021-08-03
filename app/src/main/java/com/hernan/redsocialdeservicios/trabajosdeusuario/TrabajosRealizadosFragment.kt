package com.hernan.redsocialdeservicios.trabajosdeusuario

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.MainActivity
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.clases.MainViewModel
import com.hernan.redsocialdeservicios.databinding.FragmentTrabajosRealizadosBinding
import kotlinx.android.synthetic.main.dialog_cargar_imagen.view.*
import kotlinx.android.synthetic.main.toolbar_trabajos_usuario.*


class TrabajosRealizadosFragment : Fragment() {

    private var idUsuario: String? = null
    private var emailUsuario: String? = null
    private var imagenUnoTrabajo: String? = null
    private var imagenUsuario: String? = null

    lateinit var data:String
    var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter:AdapterTrabajo? = null
   private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var binding:FragmentTrabajosRealizadosBinding

    lateinit var db: FirebaseFirestore

    var getUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrabajosRealizadosBinding.inflate(inflater, container, false)


        inflarRecyclerView()
        imagenUsuario = getUser?.photoUrl.toString()
        emailUsuario = getUser?.email.toString()
        idUsuario = getUser?.uid.toString()

        binding.ImagenUsuario.setOnClickListener { logout() }
        observeData()
        datFirebase()
        enlazarVistas(data)
        eventosToolbar()
        return binding.root
    }
    fun eventosToolbar(){

        activity?.iconCargarTrabajos?.setOnClickListener { cargarActividad() }
        activity?.iconEditarUsuario?.setOnClickListener { inflarFragmentEditar() }
    }

    private fun inflarRecyclerView() {

        layoutManager = GridLayoutManager(activity, 1)
            binding.recyclerTrabajos.layoutManager = layoutManager
            binding.recyclerTrabajos.setHasFixedSize(true)
            adapter = AdapterTrabajo(arrayListOf(), context as FragmentActivity)
            binding.recyclerTrabajos.adapter = adapter



    }

    private fun datFirebase(){
        data = ""

        val uid = getUser?.uid
        db = FirebaseFirestore.getInstance()
        db.collection("DatosDeUsuarios").whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    data = document.data["fotoUsuario"].toString()

                    enlazarVistas(data)
                }
            }
            .addOnFailureListener { exception ->
            }
    }
    fun enlazarVistas(fotoUsuario: String) {
        if (fotoUsuario.isNullOrEmpty()){
            Glide.with(requireContext().applicationContext).load(imagenUsuario).into(binding.ImagenUsuario)

        }else{
            Glide.with(requireContext().applicationContext).load(fotoUsuario).into(binding.ImagenUsuario)

        }

    }
    fun observeData() {
        mainViewModel.fetchUserData(idUsuario.toString()).observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter?.listTrabajo =it as ArrayList<ModeloTrabajos>
            adapter?.notifyDataSetChanged()
            var imagen = ""

            for (i in it){
                imagen = i.imagenPrincipal
            }

            if (imagen.isEmpty()){
                binding.imagenAgregarTrabajo.visibility = View.VISIBLE
                binding.recyclerTrabajos.visibility = View.GONE
                cargarActividadDialog()

            }else{
                binding.recyclerTrabajos.visibility = View.VISIBLE


            }

        })

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)

        activity?.finish()
    }
     private fun cargarActividad(){
         val cargarActividad = CargarTrabajoFragment()
         activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.trabajosContenedor, cargarActividad)?.
                 setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
     }
    private fun cargarActividadDialog(){
        val dialog =LayoutInflater.from(activity).inflate(R.layout.dialog_cargar_imagen, null)
        val constructorDialog = AlertDialog.Builder(activity).setView(dialog)
        // mostrar dialog.
        val alertDialog = constructorDialog.show()

        dialog.btCargarActividad.setOnClickListener { cargarActividad()
            alertDialog.dismiss()
        }


    }
    private fun inflarFragmentEditar(){
        val editFragment = EditarUsuarioFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.trabajosContenedor, editFragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
    }


}