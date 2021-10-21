package com.hernan.redsocialdeservicios.murogeneral

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.clases.fotoPerfilFirebase
import com.hernan.redsocialdeservicios.clases.nombrePerfilFirebase
import com.hernan.redsocialdeservicios.databinding.FragmentComentariosBinding
import com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.ComentarioViewModel
import com.hernan.redsocialdeservicios.murogeneral.clases_comentarios.SubirComentarios

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID_DOCUMENT = "idDocument"

class comentariosFragment : Fragment() {

    private var idDocument: String? = null
    lateinit var subirComen:SubirComentarios
    var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: AdapterComentarios? = null
    //private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val viewModel: ComentarioViewModel by viewModels()

    private lateinit var binding:FragmentComentariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idDocument = it.getString(ID_DOCUMENT)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComentariosBinding.inflate(inflater, container, false)

        subirComen = SubirComentarios()
        idDocument = arguments?.getString(ID_DOCUMENT)
        Log.e("ID DOCUMENT", idDocument.toString())

        layoutManager = GridLayoutManager(activity, 1)
        binding.recyclerComentarios.layoutManager = layoutManager
        binding.recyclerComentarios.setHasFixedSize(true)
        adapter  = AdapterComentarios(arrayListOf(), context as FragmentActivity)
        binding.recyclerComentarios.adapter = adapter

        binding.btEnviarComentario.setOnClickListener {
            if (binding.editTextComentar.text.isNotEmpty()){
                enviarUnComentario()
                binding.editTextComentar.setText("")
            }else{
                Toast.makeText(context, "comenta algo, por favor", Toast.LENGTH_SHORT).show()
            }


        }
        initObserverrs()


        return binding.root
    }

    companion object {

        fun newInstance(idDocument: String) =
            comentariosFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_DOCUMENT, idDocument)
                }
            }
    }

    fun enviarUnComentario() {
        val comentario = binding.editTextComentar.text.toString()

        val getUser = FirebaseAuth.getInstance().currentUser
        var fotoUsGoogle = getUser?.photoUrl.toString()
        var nombreEmisorGoogle = getUser?.displayName.toString()
        if (nombreEmisorGoogle.isEmpty()){
            nombreEmisorGoogle = nombrePerfilFirebase.toString()
            fotoUsGoogle = fotoPerfilFirebase.toString()
            subirComen.subirComent(nombreEmisorGoogle, idDocument.toString(), fotoUsGoogle, comentario)


        }else{
            subirComen.subirComent(nombreEmisorGoogle, idDocument.toString(), fotoUsGoogle, comentario)


        }

    }

//.......................................................


    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserverrs() {
        viewModel.comentario.observe(viewLifecycleOwner) {
            //Log.e("PRueba", it.texto.toString())
            when(it.type) {
                ModeloCmentarios.TYPE.ADD -> adapter?.arrayComentarios?.add(it)
                ModeloCmentarios.TYPE.UPDATE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayComentarios?.set(pos, it)
                    }
                }
                ModeloCmentarios.TYPE.REMOVE -> {
                    val pos = adapter?.getIndex(it)
                    if(pos!=null && pos > -1) {
                        adapter?.arrayComentarios?.removeAt(pos)
                    }
                }
            }
            adapter?.notifyDataSetChanged()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("ErrorPrueba", it.toString())
        }

        viewModel.getComentarios(idDocument)
    }
    private fun removeObservers() {
        viewModel.comentario.removeObservers(this)
        viewModel.error.removeObservers(this)
        viewModel.removeListener()
    }

}