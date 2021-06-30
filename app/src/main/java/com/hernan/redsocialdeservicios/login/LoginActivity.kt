package com.hernan.redsocialdeservicios.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflarFragmetLogin()



    }

    private fun inflarFragmetLogin(){
        supportFragmentManager.beginTransaction().replace(R.id.contenedor, LoginFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }


}