package com.hernan.redsocialdeservicios.murogeneral

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.clases.MainViewModel
import com.hernan.redsocialdeservicios.databinding.FragmentMuroPrincipalBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.AdapterTrabajo
import com.hernan.redsocialdeservicios.trabajosdeusuario.ModeloTrabajos


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MuroPrincipalFragment : Fragment() {
    var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: AdapterMuroPrincipal? = null
    private val muroViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentMuroPrincipalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuroPrincipalBinding.inflate(inflater, container, false)

        inflarRecyclerMuro()
        obtenerDataMuro()
        return binding.root
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            MuroPrincipalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun inflarRecyclerMuro(){
        layoutManager = GridLayoutManager(activity, 1)
        binding.recyclerMuroPrincipal.layoutManager = layoutManager
        binding.recyclerMuroPrincipal.setHasFixedSize(true)
        adapter = AdapterMuroPrincipal(arrayListOf(), context as FragmentActivity)
        binding.recyclerMuroPrincipal.adapter = adapter

    }
    fun obtenerDataMuro(){
        muroViewModel.UserDataMuro().observe(this.viewLifecycleOwner, Observer{
            adapter?.arrayMuro = it as ArrayList<ModeloTrabajos>
            adapter?.notifyDataSetChanged()
        })
    }
}