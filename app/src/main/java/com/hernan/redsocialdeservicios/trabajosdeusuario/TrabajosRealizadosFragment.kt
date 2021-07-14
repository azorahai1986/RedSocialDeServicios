package com.hernan.redsocialdeservicios.trabajosdeusuario

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.MainActivity
import com.hernan.redsocialdeservicios.databinding.FragmentTrabajosRealizadosBinding




class TrabajosRealizadosFragment : Fragment() {

    private var idUsuario: String? = null
    private var emailUsuario: String? = null
    private var imagenUsuario: String? = null


    var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter:AdapterTrabajo? = null
   private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var binding:FragmentTrabajosRealizadosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrabajosRealizadosBinding.inflate(inflater, container, false)

        layoutManager = GridLayoutManager(activity, 1)
        binding.recyclerTrabajos.layoutManager = layoutManager
        binding.recyclerTrabajos.setHasFixedSize(true)
        adapter = AdapterTrabajo(arrayListOf(), context as FragmentActivity)
        binding.recyclerTrabajos.adapter = adapter

        val getUser = FirebaseAuth.getInstance().currentUser
        imagenUsuario = getUser?.photoUrl.toString()
        emailUsuario = getUser?.email.toString()
        idUsuario = getUser?.uid.toString()
       // Log.e("IDUSUARIOTRABAJOS", idUsuario.toString())

        observeData()

        binding.ImagenUsuario.setOnClickListener { logout() }
        enlazarVistas()
        return binding.root
    }

    private fun enlazarVistas(){

        Glide.with(requireContext().applicationContext).load(imagenUsuario).into(binding.ImagenUsuario)

    }
    fun observeData() {
        mainViewModel.fetchUserData(idUsuario.toString()).observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter?.listTrabajo =it as ArrayList<ModeloTrabajos>

            adapter?.notifyDataSetChanged()

        })

    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)

        activity?.finish()
    }


}