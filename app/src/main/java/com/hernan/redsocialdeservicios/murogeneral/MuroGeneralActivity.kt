package com.hernan.redsocialdeservicios.murogeneral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.ActivityMuroGeneralBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.TrabajosDelUsuarioActivity
import kotlinx.android.synthetic.main.activity_muro_general.*
import kotlinx.android.synthetic.main.toolbar_muro_general.*
import kotlinx.android.synthetic.main.toolbar_muro_general.view.*
import kotlinx.android.synthetic.main.toolbar_trabajos_usuario.*

class MuroGeneralActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMuroGeneralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muro_general)
        binding = ActivityMuroGeneralBinding.inflate(layoutInflater)
        inflarFragment()
        inflarToolbarMuro()
    }

    private fun inflarFragment(){
        val fragmentMuro = MuroPrincipalFragment()
        supportFragmentManager.beginTransaction().replace(R.id.containerMuroGral, fragmentMuro)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }
    private fun inflarToolbarMuro(){
        setSupportActionBar(toolbarMuroGral)
        val actionBar = supportActionBar
        toolbarMuro.iconCargarTrabajos.setOnClickListener { irTrabajosUsuario() }

    }
    private fun irTrabajosUsuario(){
        val intent = Intent(this, TrabajosDelUsuarioActivity::class.java)

        startActivity(intent)
    }
}