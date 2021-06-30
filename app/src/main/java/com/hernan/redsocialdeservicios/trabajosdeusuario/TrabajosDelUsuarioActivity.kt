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

        val bundle = intent.extras
        val email = bundle?.getString("EMAIL")
        val idUsuario = bundle?.getString("IDUSUARIO")


        Log.e("Email recibido", email.toString())
        Log.e("ID recibido", idUsuario.toString())
        inflarFragment(email, idUsuario)

    }

    fun inflarFragment(email: String?, idUsuario: String?) {

        fragmentTrabajos = TrabajosRealizadosFragment()

        supportFragmentManager?.beginTransaction()?.replace(R.id.contenedor,
            TrabajosRealizadosFragment.newInstance(idUsuario.toString(), email.toString()))?.
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
    }
}