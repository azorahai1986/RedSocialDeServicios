package com.hernan.redsocialdeservicios.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentRegistrarGoogleBinding

class RegistrarGoogleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegistrarGoogleBinding

    private var emailRecibido: String? = null
    private var nombreRecibido: String? = null
    private var fotoRecibida: String? = null
    private var authRecibido: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrarGoogleBinding.inflate(inflater, container, false)
        binding.btRegistrarGoogle.setOnClickListener { irDatosPersonales() }


        obtenerAuthUsuario()


        return binding.root
    }


    fun obtenerAuthUsuario(){

        auth = Firebase.auth
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            emailRecibido = user.email
            nombreRecibido = user.displayName
            authRecibido = user.uid
            fotoRecibida = user.photoUrl?.toString()

        }
        Glide.with(requireContext().applicationContext).load(fotoRecibida).into(binding.imagenGoogleRegis)
        binding.textoGoogleRegis.text = emailRecibido.toString()
        binding.TextNombreGoogle.text = nombreRecibido.toString()


    }

    fun irDatosPersonales(){
        val datosPersonalesFragment = DatosPersonalesFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor,
            datosPersonalesFragment)?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }



}