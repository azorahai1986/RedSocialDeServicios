package com.hernan.redsocialdeservicios.trabajosdeusuario.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.MainActivity
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentTrabajosRealizadosBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.AdapterTrabajo
import com.hernan.redsocialdeservicios.trabajosdeusuario.adaptersymodelos.ModeloTrabajos
import com.hernan.redsocialdeservicios.trabajosdeusuario.clases_trabajos_realizados.TrabajosRealizadosViewModel
import kotlinx.android.synthetic.main.toolbar_trabajos_usuario.*


class TrabajosRealizadosFragment : Fragment() {

    private var idUsuario: String? = null
    private var emailUsuario: String? = null
    private var imagenUsuario: String? = null
    private var idDocument: String? = null
    var imagenTrabajo:String? = null

    var data:String? = null
    lateinit var dataNombreUsuario:String
    var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter: AdapterTrabajo? = null
    private val viewModel: TrabajosRealizadosViewModel by viewModels()
    private lateinit var binding:FragmentTrabajosRealizadosBinding

    lateinit var db: FirebaseFirestore

    var getUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrabajosRealizadosBinding.inflate(inflater, container, false)


        //inflarRecyclerViewTrabRealiz()
        imagenUsuario = getUser?.photoUrl.toString()
        emailUsuario = getUser?.email.toString()
        idUsuario = getUser?.uid.toString()


        binding.ImagenUsuario.setOnClickListener { logout() }
        datFirebaseTrabRealiz()
        eventosToolbar()
        initObserverrs()
        inflarRecyclerViewTrabRealiz()

        return binding.root
    }
    fun eventosToolbar(){

        activity?.iconCargarTrabajos?.setOnClickListener { cargarActividad() }
        activity?.iconEditarUsuario?.setOnClickListener { inflarFragmentEditar() }
    }

    fun inflarRecyclerViewTrabRealiz() {

        layoutManager = GridLayoutManager(activity, 4)
            binding.recyclerTrabajos.layoutManager = layoutManager
            binding.recyclerTrabajos.setHasFixedSize(true)
            adapter = AdapterTrabajo(arrayListOf(), context as FragmentActivity)
            binding.recyclerTrabajos.adapter = adapter



    }

    fun datFirebaseTrabRealiz() {
        data = ""
        dataNombreUsuario = ""

        val uid = getUser?.uid
        db = FirebaseFirestore.getInstance()
        db.collection("DatosDeUsuarios").whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    data = document.data["fotoUsuario"].toString()
                    dataNombreUsuario = document.data["nombreYapellido"].toString()


                    //enlazarVistas(data, "")

                    if (data!!.isEmpty()){
                        Glide.with(requireContext().applicationContext).load(imagenUsuario).into(binding.ImagenUsuario)

                    }else{
                        Glide.with(requireContext().applicationContext).load(data!!).into(binding.ImagenUsuario)

                    }

                    binding.tvNombreUsuaruio.text = dataNombreUsuario
                    binding.tvEmailUsuario.text = emailUsuario
                }
            }
            .addOnFailureListener { exception ->
            }


        db.collection("TrabajosDelUsusario").whereEqualTo("id", uid).get().addOnSuccessListener { documents ->
            for (document in documents){
                idDocument = document.id
                //Log.e("IIDDD", idDocument.toString())

            }

        }
    }

    fun enlazarVistas(imagenPrincipal: String) {




        when (imagenPrincipal) {
            "" -> {

            }
            else -> {

                binding.recyclerTrabajos.visibility = View.VISIBLE
                binding.imagenAgregarTrabajo.visibility = View.GONE

            }
        }



    }


    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)

        activity?.finish()
    }
     private fun cargarActividad(){
         val cargarActividad = CargarTrabajoFragment()
         activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.trabajosContenedor, cargarActividad)?.
                 setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
     }

    private fun inflarFragmentEditar(){
        val editFragment = EditarUsuarioFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.trabajosContenedor, editFragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
    }



    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrs() {

        viewModel.fetchTrabajos(idUsuario).observe(viewLifecycleOwner) {
            adapter?.listTrabajo = it as ArrayList<ModeloTrabajos>
            for (i in it){
                enlazarVistas(i.imagenPrincipal)
            }

            adapter?.notifyDataSetChanged()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("ErrorPrueba", it.toString())
        }

        viewModel.getTrabajoRealizado(idUsuario)



    }
    private fun removeObservers() {
        viewModel.traRealizado.removeObservers(this)
        viewModel.error.removeObservers(this)
        viewModel.removeListener()
    }


}