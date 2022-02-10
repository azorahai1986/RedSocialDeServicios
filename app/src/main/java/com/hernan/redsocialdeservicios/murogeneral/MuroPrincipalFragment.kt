package com.hernan.redsocialdeservicios.murogeneral

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.databinding.FragmentMuroPrincipalBinding
import com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos.AdapterEncabezado
import com.hernan.redsocialdeservicios.murogeneral.clasesmuro.MuroViewModel
import com.hernan.redsocialdeservicios.murogeneral.encabezado.adapters_y_modelos.ModeloEncabezado
import com.hernan.redsocialdeservicios.murogeneral.encabezado.firebase.EncabezadoViewModel
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import java.util.*


class MuroPrincipalFragment : Fragment() {
    var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: AdapterMuroPrincipal? = null
    private var adapterEncabezado: AdapterEncabezado? = null
    private val viewModel: MuroViewModel by viewModels()
    private val viewModelEncabezado: EncabezadoViewModel by viewModels()

    var getUser = FirebaseAuth.getInstance().currentUser

    private lateinit var binding:FragmentMuroPrincipalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuroPrincipalBinding.inflate(inflater, container, false)

        inflarRecyclerMuro()
        inflarRecyclerEncabezado()
        initObserverrs()
        initObserverrEncabezado()
        return binding.root
    }

    private fun inflarRecyclerMuro(){
        layoutManager = GridLayoutManager(activity, 1)
        binding.recyclerMuroPrincipal.layoutManager = layoutManager
        binding.recyclerMuroPrincipal.setHasFixedSize(true)
        adapter = AdapterMuroPrincipal(arrayListOf(), context as FragmentActivity)
        binding.recyclerMuroPrincipal.adapter = adapter


    }

    private fun inflarRecyclerEncabezado(){
        layoutManager = LinearLayoutManager(activity,  RecyclerView.HORIZONTAL, false)
        binding.rcyclerEncabezado.layoutManager = layoutManager
        binding.rcyclerEncabezado.setHasFixedSize(true)
        adapterEncabezado = AdapterEncabezado(arrayListOf(), context as FragmentActivity)
        binding.rcyclerEncabezado.adapter = adapterEncabezado
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
        removeObserversEncabezado()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrs() {
        viewModel.muro.observe(viewLifecycleOwner) {
            //Log.e("PRueba", it.texto.toString())
            when(it.type) {
                ModeloTrabajos.TYPE.ADD -> adapter?.arrayMuro?.add(it)
                ModeloTrabajos.TYPE.UPDATE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayMuro?.set(pos, it)

                    }
                }
                ModeloTrabajos.TYPE.REMOVE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayMuro?.removeAt(pos)
                    }
                }
            }

            adapter?.notifyDataSetChanged()

        }


        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("ErrorPrueba", it.toString())
        }

        viewModel.getMuro()
    }

    private fun removeObservers() {
        viewModel.muro.removeObservers(this)
        viewModel.error.removeObservers(this)
        viewModel.removeListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrEncabezado() {
        viewModelEncabezado.encabezado.observe(viewLifecycleOwner) {
            //Log.e("PRueba", it.texto.toString())
            when(it.type) {
                ModeloEncabezado.TYPE.ADD -> adapterEncabezado?.arrayEncabezado?.add(it)
                ModeloEncabezado.TYPE.UPDATE -> {
                    val pos = adapterEncabezado?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapterEncabezado?.arrayEncabezado?.set(pos, it)
                    }
                }
                ModeloEncabezado.TYPE.REMOVE -> {
                    val pos = adapterEncabezado?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapterEncabezado?.arrayEncabezado?.removeAt(pos)
                    }
                }
            }

            adapterEncabezado?.notifyDataSetChanged()

        }
        viewModelEncabezado.error.observe(viewLifecycleOwner) {
            Log.e("ErrorPrueba", it.toString())
        }

        viewModelEncabezado.getEncabezado()
    }
    private fun removeObserversEncabezado() {
        viewModelEncabezado.encabezado.removeObservers(this)
        viewModelEncabezado.error.removeObservers(this)
        viewModelEncabezado.removeListener()
    }

}