
package com.hernan.redsocialdeservicios.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.databinding.FragmentRestablecerPaswordBinding
import com.hernan.redsocialdeservicios.login.clases_login.RecuperarPassword

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [RestablecerPaswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestablecerPaswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentRestablecerPaswordBinding
    lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRestablecerPaswordBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        binding.btVerificar.setOnClickListener { restablecerContraseña("") }
        return binding.root
    }

    fun restablecerContraseña(b: String) {

        val emailVerification = binding.etEmail.text.toString()
        val recoveryPassword = RecuperarPassword()
        recoveryPassword.recoveryPassword(emailVerification)
            if (b == "correcto"){
                Toast.makeText(context, "se envió un mail a tu casilla de correo",Toast.LENGTH_SHORT).show()
                activity?.finishAfterTransition()
            }else{
                Toast.makeText(context, "No es un mail valido. asegurate de ingresar uno existente",Toast.LENGTH_SHORT).show()

            }

    }


}