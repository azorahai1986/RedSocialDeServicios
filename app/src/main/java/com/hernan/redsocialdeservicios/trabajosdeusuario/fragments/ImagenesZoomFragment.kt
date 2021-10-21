package com.hernan.redsocialdeservicios.trabajosdeusuario.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.databinding.FragmentImagenesZoomBinding
import com.hernan.redsocialdeservicios.murogeneral.AdapterMuroPrincipal
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados.TrabajosRealizadosViewModel

private const val IMAGENES = "imagenes"
private const val IDDOC = "idDoc"

class ImagenesZoomFragment : Fragment() {

    lateinit var binding:FragmentImagenesZoomBinding
    var adapter:AdapterMuroPrincipal? = null
    //private var imagenes: ArrayList<String>? = null
    private var imagenes: ArrayList<ModeloTrabajos>? = null
    private var idDoc: String? = null
    private var posicion:String? = null
    private var getUser = FirebaseAuth.getInstance().currentUser
    private var layoutManager:RecyclerView.LayoutManager? = null
    private val viewModel: TrabajosRealizadosViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //imagenes = it.getStringArrayList(IMAGENES)
            idDoc = it.getString(IDDOC)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagenesZoomBinding.inflate(layoutInflater, container, false)


        idDoc = arguments?.getString(IDDOC)
        initObserverrs()
        inflarRecycler()

        return binding.root
    }

    private fun inflarRecycler(){
        layoutManager = LinearLayoutManager(activity,  RecyclerView.VERTICAL, false)
        binding.recyclerZoom.layoutManager = layoutManager
       // binding.recyclerZoom.setHasFixedSize(true)
        adapter = AdapterMuroPrincipal(arrayListOf(), context as FragmentActivity)
        binding.recyclerZoom.adapter = adapter




    }

    fun indexarRecycler(): Int {

        for (i in 0 until adapter!!.arrayMuro.size){

            if (adapter!!.arrayMuro[i].id == idDoc){
               // Log.e("ViewModelo IDDOC2", adapter!!.arrayMuro[i].id)
               // Log.e("ViewModelo I", i.toString())

                return i
            }
        }
        return -1

    }




    companion object {

        @JvmStatic
        fun newInstance(idDoc: String) =
            ImagenesZoomFragment().apply {
                arguments = Bundle().apply {
                    putString(IDDOC, idDoc)
                }
            }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrs() {
        val uid = getUser?.uid

        viewModel.traRealizado.observe(viewLifecycleOwner) {

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
            // para scrollear el recycler hasta la posicion deseada
            if (idDoc != null){
                val i = indexarRecycler()

                if (i >- 1){
                    Log.e("ErrorPrueba", it.toString())
                    Log.e("ErrorPrueba", i.toString())

                    binding.recyclerZoom.layoutManager?.scrollToPosition(i)


                }
            }

            adapter?.notifyDataSetChanged()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("ErrorPrueba", it.toString())
        }


        viewModel.getTrabajoRealizado(uid)



    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
    private fun removeObservers() {
        viewModel.traRealizado.removeObservers(this)
        viewModel.error.removeObservers(this)
        viewModel.removeListener()
    }


}