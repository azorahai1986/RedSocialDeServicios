package com.hernan.redsocialdeservicios.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentRegistrarUsuarioBinding
import java.util.regex.Pattern



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegistrarUsuarioFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentRegistrarUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrarUsuarioBinding.inflate(inflater, container, false)


        binding.btRegistrarUusario.setOnClickListener {
            validate(binding.etEmailRegistrarse.text.toString(), binding.etPasswordRegistrarse.text.toString())
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrarUsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun registrarUsuario(emailtext: String, uidText: String) {
        var emailExitoso = ""
        var uidDelUsuario = ""
        var prvider = ProviderType.GOOGLE
        var error:String = "ERROR"


        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(binding.etEmailRegistrarse.text.toString(),
                binding.etPasswordRegistrarse.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){

                    emailExitoso = it.result?.user?.email?: ""
                    prvider = ProviderType.GOOGLE
                    uidDelUsuario = it.result?.user?.uid?: ""

                    Log.e("EMAILREGISTRADO", emailExitoso)
                    Log.e("UIDREGISTRADO", uidDelUsuario)
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.contenedor,
                        DatosPersonalesFragment.newInstance(emailExitoso, uidDelUsuario))?.
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()


                }else{
                    errorUsuarioDialog(error)

                }

            }
    }

    private fun validate(emailText: String, uidText: String) {
        val result = arrayOf(validarEmail(), validarPassword())
        if (false in result){
            return
        }else{
            Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
            registrarUsuario(emailText, uidText)
        }

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
    private fun validarPassword():Boolean{
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