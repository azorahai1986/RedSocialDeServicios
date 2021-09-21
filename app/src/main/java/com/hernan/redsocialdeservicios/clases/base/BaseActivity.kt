package com.hernan.redsocialdeservicios.clases.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


// esta clase me servirá para todas las funciones o procesos que requiera repetir en varios archivos
// actividades o fragments. debo extender AppCompat aquí

abstract class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    protected abstract fun getViewId():Int

    fun showProgress(){
        progresBar.visibility = View.VISIBLE
    }
    fun hideProgress(){
        progresBar.visibility = View.GONE
    }
}