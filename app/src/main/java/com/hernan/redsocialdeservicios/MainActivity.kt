package com.hernan.redsocialdeservicios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.clases.base.BaseActivity
import com.hernan.redsocialdeservicios.clases.data.RepoImplement
import com.hernan.redsocialdeservicios.clases.domain.UserCaseImplement
import com.hernan.redsocialdeservicios.clases.presentation.viewmodel.MViemodel
import com.hernan.redsocialdeservicios.clases.presentation.viewmodel.ViewMoselFactory
import com.hernan.redsocialdeservicios.clases.vo.Resource
import com.hernan.redsocialdeservicios.databinding.ActivityMainBinding
import com.hernan.redsocialdeservicios.login.LoginActivity
import com.hernan.redsocialdeservicios.murogeneral.MuroGeneralActivity


class MainActivity : BaseActivity() {
    // usaré un viewmodel distinto del de siempre para usar orrutinas con el
    private val viewModel by lazy { ViewModelProvider(this, ViewMoselFactory(UserCaseImplement(RepoImplement()))).get(MViemodel::class.java) }
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val callbackManager = CallbackManager.Factory.create()
    private val GOOGLE_SIGN_IN = 100

    private var emailObtenido:String? = null
    private var uidObtenido:String? = null
    private var imagenObtenido:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        emailObtenido = ""

        observeData()
            binding.pasarActividad.setOnClickListener {verificarRegistroUsuario() }



    }

    override fun getViewId(): Int = R.layout.activity_main
    private fun observeData(){
        viewModel.fetcVersion.observe(this, Observer { result: Resource<Int> ->
            when(result){
                is Resource.Loading ->{
                    showProgress()
                }
                is Resource.Success ->{
                    binding.textVersion.text = result.data.toString()
                    hideProgress()
                }
                is Resource.Failure ->{
                    Toast.makeText(this, "error ${result.exception.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun verificarRegistroUsuario() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            emailObtenido = user.email
            uidObtenido = user.uid
            imagenObtenido = user.photoUrl?.toString()

        }

        if (emailObtenido.isNullOrEmpty()){
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }else{
            val intent = Intent(this, MuroGeneralActivity::class.java)
            intent.putExtra("EMAIL", emailObtenido)
            intent.putExtra("IDUSUARIO", uidObtenido)
            intent.putExtra("IMAGEN", imagenObtenido)
            startActivity(intent)
        }

        Log.e("EMAIL_MAIN", emailObtenido.toString())
        Log.e("ID_MAIN", uidObtenido.toString())
        Log.e("ID_FOTO", imagenObtenido.toString())
    }


}