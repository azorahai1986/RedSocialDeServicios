package com.hernan.redsocialdeservicios.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.clases.clase_registrar.isValidEmail
import com.hernan.redsocialdeservicios.clases.clase_registrar.isValidPassword
import com.hernan.redsocialdeservicios.clases.clase_registrar.validater
import com.hernan.redsocialdeservicios.databinding.FragmentLoginBinding
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.RegistrarGoogleFragment
import com.hernan.redsocialdeservicios.registrar_usuario.fragmentos.RegistrarUsuarioFragment
import com.hernan.redsocialdeservicios.login.fragments.RestablecerPaswordFragment
import com.hernan.redsocialdeservicios.murogeneral.MuroGeneralActivity
import java.util.regex.Pattern


enum class ProviderType{
    GOOGLE,
    FACEBOOK
}

class LoginFragment : Fragment() {

    //para entrar con google.............
    private val GOOGLE_SIGN_IN = 100
    private lateinit var auth: FirebaseAuth



    // calbackManager es una clase de facebook.............
    private val callbackManager = CallbackManager.Factory.create()


    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        binding.cardViewRegistrarse.setOnClickListener { fragmentRegistrarse() }
        binding.cardViewGoogle.setOnClickListener { ingresarconGoogle() }



        binding.textPasswordRecovery.setOnClickListener { irAValidarPasword() }
        binding.etEmail.validater {
            binding.etEmail.error = if (isValidEmail(it)) null else "el mail no es valido"
        }
        binding.etPassword.validater {
            binding.etPassword.error = if (isValidPassword(it)) null else "debe haber por lo menos una mayuscula, un numero"
        }
        binding.btAcceder.setOnClickListener {

            if (isValidEmail(binding.etEmail.text.toString()) && isValidPassword(binding.etPassword.text.toString())){
                ingresarUs(binding.etEmail.text.toString(), binding.etPassword.text.toString())

            }else{
                Toast.makeText(context, "verifia que los datos sean correctos", Toast.LENGTH_SHORT).show()
            }


        }

        return binding.root
    }

    fun ingresarUs(etEmail: String, etPassword:String) {

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(etEmail.toString(), etPassword.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    if (auth.currentUser!!.isEmailVerified){

                    }

                    val intent = Intent(context, MuroGeneralActivity::class.java)
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
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor, RegistrarUsuarioFragment())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }
    fun irAValidarPasword(){
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor, RestablecerPaswordFragment())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
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
        auth = Firebase.auth

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

                            val email = account.email ?:""
                            val uidGoogle = auth.currentUser?.uid ?:""
                            val googleFoto = account.photoUrl.toString()


                            val registrarGoogleFragment= RegistrarGoogleFragment()
                            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor,
                                registrarGoogleFragment)?.
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
                        }else{

                        }
                    }


                }

            }catch (e:ApiException){

            }

        }


    }
}
