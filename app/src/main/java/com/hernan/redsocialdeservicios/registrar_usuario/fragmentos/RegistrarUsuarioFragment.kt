package com.hernan.redsocialdeservicios.registrar_usuario.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.FragmentTransaction
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentRegistrarUsuarioBinding
import com.hernan.redsocialdeservicios.clases.clase_registrar.CreateUser
import java.util.regex.Pattern


class RegistrarUsuarioFragment : Fragment()  {
    lateinit var createUser: CreateUser

    private lateinit var binding:FragmentRegistrarUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrarUsuarioBinding.inflate(inflater, container, false)

       // create = CreateUser()

        binding.btRegistrarUusario.setOnClickListener {
            validate()

        }



        return binding.root
    }




    private fun validate() {
        val result = arrayOf(validarEmail(), validarPassword(false))
        if (false in result){
            return
        }else{
            Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
            var email = binding.etEmailRegistrarse.text.toString()
            var pasword = binding.etPasswordRegistrarse.text.toString()
            irDatosPersonales(email, pasword)
            createUser = CreateUser()
            createUser.createUser(email.toString(),pasword.toString())
        }

    }
    fun irDatosPersonales(email: String, pasword: String) {

        //Log.e("EMAILREGISTRADO", )


        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor,
            DatosPersonalesFragment.newInstance(email, pasword))?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()

    }
    private fun validarEmail():Boolean{

        val validEmail = binding.etEmailRegistrarse.text.toString()
        return if (validEmail.isEmpty()) {
            binding.etEmailRegistrarse.error = "campo vacio. ingrese un email"
            false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(validEmail).matches()) {
            binding.etEmailRegistrarse.error = "Ingrese un email valido"
            false
        } else {
            binding.etEmailRegistrarse.error = null
            true
        }

    }
    fun validarPassword(b: Boolean):Boolean {
        val password = binding.etPasswordRegistrarse.text.toString()
        val passwordRegex = Pattern.compile(
            "^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-Z])"+
                    "(?=\\S+$)"+
                    ".{4,}"+
                    "$"
        )

        return if (password.isEmpty()) {
            binding.etPasswordRegistrarse.error = "campo vacio. ingrese una contraseña"
            false
        } else if (!passwordRegex.matcher(password).matches()) {
            binding.etPasswordRegistrarse.error = "la contraseña es poco segura"
            false
        } else {
            binding.etPasswordRegistrarse.error = null
            true
        }
    }

    fun errorUsuarioDialog(error: String) {
        Log.e("ERROR", error)

    }

}