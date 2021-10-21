package com.hernan.redsocialdeservicios.trabajosdeusuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.trabajosdeusuario.fragments.TrabajosRealizadosFragment
import kotlinx.android.synthetic.main.toolbar_trabajos_usuario.*

class TrabajosDelUsuarioActivity : AppCompatActivity() {

    lateinit var fragmentTrabajos: TrabajosRealizadosFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajos_del_usuario)


        inflarToolbar()
        inflarFragment()

    }

    fun inflarFragment() {

        fragmentTrabajos = TrabajosRealizadosFragment()

        supportFragmentManager?.beginTransaction()?.replace(R.id.trabajosContenedor, fragmentTrabajos)?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }

    private fun inflarToolbar(){
        setSupportActionBar(toolbar_trab_usuario)
        val actionBar = supportActionBar


    }
}