package com.hernan.redsocialdeservicios.registrar_usuario.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentRegistrarUsuarioBinding
import com.hernan.redsocialdeservicios.clases.clase_registrar.CreateUser
import com.hernan.redsocialdeservicios.clases.clase_registrar.isValidEmail
import com.hernan.redsocialdeservicios.clases.clase_registrar.isValidPassword
import com.hernan.redsocialdeservicios.clases.clase_registrar.validater


class RegistrarUsuarioFragment : Fragment()  {
    lateinit var createUser: CreateUser

    private lateinit var binding:FragmentRegistrarUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrarUsuarioBinding.inflate(inflater, container, false)

       createUser = CreateUser()


        binding.etEmailRegistrarse.validater {
           binding.etEmailRegistrarse.error = if (isValidEmail(it)) null else "el mail no es valido"
        }
        binding.etPasswordRegistrarse.validater {
           binding.etPasswordRegistrarse.error = if (isValidPassword(it)) null else "debe haber por lo menos una mayuscula, un numero"
        }
        binding.btRegistrarUusario.setOnClickListener {

            if (isValidEmail(binding.etEmailRegistrarse.text.toString()) && isValidPassword(binding.etPasswordRegistrarse.text.toString())){
                irDatosPersonales(binding.etEmailRegistrarse.text.toString(), binding.etPasswordRegistrarse.text.toString())
                createUser.createUser(binding.etEmailRegistrarse.text.toString(), binding.etPasswordRegistrarse.text.toString())

            }else{
                Toast.makeText(context, "verifia que los datos sean correctos", Toast.LENGTH_SHORT).show()
            }


        }


        return binding.root
    }


    fun irDatosPersonales(email: String, pasword: String) {


        //Log.e("Create user Log", createUser.toString())

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor,
            DatosPersonalesFragment.newInstance(email, pasword))?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()

    }

    fun errorUsuarioDialog(error: Task<Void>) {
        Toast.makeText(context, "se envió un mail de verificación a tu casilla de correo", Toast.LENGTH_SHORT).show()

        Log.e("ERROR", error.toString())

    }
    override fun getReturnTransition(): Any? {
        Toast.makeText(context, "email no se pudo verifiar", Toast.LENGTH_SHORT).show()

        return super.getReturnTransition()

    }


}