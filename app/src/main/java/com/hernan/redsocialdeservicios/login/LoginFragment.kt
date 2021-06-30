package com.hernan.redsocialdeservicios.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.fragment.app.FragmentTransaction
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentLoginBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.TrabajosDelUsuarioActivity
import java.util.regex.Pattern


enum class ProviderType{
    GOOGLE,
    FACEBOOK
}
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //para entrar con google.............
    private val GOOGLE_SIGN_IN = 100

    // calbackManager es una clase de facebook.............
    private val callbackManager = CallbackManager.Factory.create()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.cardViewRegistrarse.setOnClickListener { fragmentRegistrarse() }
        binding.cardViewGoogle.setOnClickListener { ingresarconGoogle() }
        binding.btAcceder.setOnClickListener {
            if (!binding.etEmail.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()){
                validate(binding.etEmail.text, binding.etPassword.text)

            }else{
                Toast.makeText(context, "ingresa un mail y contraseña", Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun ingresarUs(etEmail: Editable, etPassword: Editable) {

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(etEmail.toString(), etPassword.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(context, TrabajosDelUsuarioActivity::class.java)
                    intent.putExtra("EMAIL", it.result?.user?.email)
                    intent.putExtra("IDUSUARIO", it.result?.user?.uid)
                    startActivity(intent)
                    activity?.finish()

                }else{
                    Toast.makeText(context, "email o contraseña incorrecto", Toast.LENGTH_SHORT).show()

                }

            }

    }



    fun fragmentRegistrarse(){
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.contenedor, RegistrarUsuarioFragment())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }

    private fun validate(etEmail: Editable, etPassword: Editable) {
        val result = arrayOf(validarEmail(), validarPassword())
        if (false in result){
            return
            Toast.makeText(context, "ingrese patrones requeridos", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()
            ingresarUs(etEmail, etPassword)
        }


    }
    private fun validarEmail():Boolean{

        val validEmail = binding.etEmail.text.toString()
        return if (validEmail.isEmpty()) {
            binding.etEmail.error = "campo vacio. ingrese un email"
            false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(validEmail).matches()) {
            binding.etEmail.error = "Ingrese un email valido"
            false
        } else {
            binding.etEmail.error = null
            true
        }

    }
    private fun validarPassword():Boolean{
        val password = binding.etPassword.text.toString()
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
            binding.etPassword.error = "campo vacio. ingrese una contraseña"
            false
        } else if (!passwordRegex.matcher(password).matches()) {
            binding.etPassword.error = "la contraseña no es valida"
            false
        } else {
            binding.etPassword.error = null
            true
        }
    }

    fun ingresarconGoogle(){
        val googleConfig = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(), googleConfig)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.e("EMAILGOOGLE", account.email ?:"")
                            Log.e("GOOGLE", ProviderType.GOOGLE.toString())
                            val intent = Intent(context, TrabajosDelUsuarioActivity::class.java)
                            intent.putExtra("email", account.email)
                            intent.putExtra("id", it.result?.user?.uid)
                            startActivity(intent)
                            activity?.finish()
                        }else{

                        }
                    }

                }

            }catch (e:ApiException){

            }

        }
    }
}
