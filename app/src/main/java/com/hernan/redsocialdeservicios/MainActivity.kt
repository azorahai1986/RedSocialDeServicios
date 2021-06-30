package com.hernan.redsocialdeservicios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.databinding.ActivityMainBinding
import com.hernan.redsocialdeservicios.login.LoginActivity
import com.hernan.redsocialdeservicios.trabajosdeusuario.TrabajosDelUsuarioActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    lateinit var emailObtenido:String
    lateinit var uidObtenido:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailObtenido = ""
        uidObtenido = ""
        auth = Firebase.auth
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            emailObtenido = user.email.toString()
            uidObtenido = user.uid

        }
            binding.pasarActividad.setOnClickListener {entrarALaApp(emailObtenido, uidObtenido)}



    }
    fun entrarALaApp(email: String, uid: String) {
        Log.e("EMAIL_MAIN", email.toString())
        Log.e("ID_MAIN", uid.toString())
        if (email.isNullOrEmpty()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, TrabajosDelUsuarioActivity::class.java)
            startActivity(intent)
        }



    }
}