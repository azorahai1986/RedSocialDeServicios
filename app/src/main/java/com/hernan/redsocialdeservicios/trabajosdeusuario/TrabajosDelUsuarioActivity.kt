package com.hernan.redsocialdeservicios.trabajosdeusuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.hernan.redsocialdeservicios.R

class TrabajosDelUsuarioActivity : AppCompatActivity() {

    lateinit var fragmentTrabajos:TrabajosRealizadosFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajos_del_usuario)


        inflarFragment()

    }

    fun inflarFragment() {

        fragmentTrabajos = TrabajosRealizadosFragment()

        supportFragmentManager?.beginTransaction()?.replace(R.id.loginContenedor, fragmentTrabajos)?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }
}