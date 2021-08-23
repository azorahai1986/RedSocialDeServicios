package com.hernan.redsocialdeservicios.murogeneral

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.clases.MainViewModel
import com.hernan.redsocialdeservicios.clases.fotoPerfilFirebase
import com.hernan.redsocialdeservicios.clases.nombrePerfilFirebase
import com.hernan.redsocialdeservicios.databinding.FragmentComentariosBinding
import com.hernan.redsocialdeservicios.murogeneral.clases.IDUSUARIO
import com.hernan.redsocialdeservicios.murogeneral.clases.SubirComentarios
import com.hernan.redsocialdeservicios.murogeneral.clases.dataFirebaseTrabajosUsuario

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID_DOCUMENT = "idDocument"

class comentariosFragment : Fragment() {


    private var idDocument: String? = null
    lateinit var subirComen:SubirComentarios
    var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: AdapterComentarios? = null
    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
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
    ): View? {
        binding = FragmentComentariosBinding.inflate(inflater, container, false)

        subirComen = SubirComentarios()
        idDocument = arguments?.getString(ID_DOCUMENT)
        Log.e("ID DOCUMENT", idDocument.toString())

        layoutManager = GridLayoutManager(activity, 1)
        binding.recyclerComentarios.layoutManager = layoutManager
        binding.recyclerComentarios.setHasFixedSize(true)
        adapter  = AdapterComentarios(arrayListOf(), context as FragmentActivity)
        binding.recyclerComentarios.adapter = adapter

        binding.btEnviarComentario.setOnClickListener { enviarUnComentario() }

        observerComentData(arraySnap)

        dataFirebaseTrabajosUsuario()


        return binding.root
    }

    private fun observerComentData(arraySnap: ModeloCmentarios) {
        mainViewModel.userDataComentarios(idDocument.toString()).observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter?.arrayComentarios =it as ArrayList<ModeloCmentarios>
            Log.e("Observer coment", it.toString())
            adapter?.arrayComentarios?.add(arraySnap)
            adapter?.notifyDataSetChanged()
        })

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
        var comentario = binding.editTextComentar.text.toString()

        val getUser = FirebaseAuth.getInstance().currentUser
        var fotoUsGoogle = getUser?.photoUrl.toString()
        var nombreEmisorGoogle = getUser?.displayName.toString()
        if (nombreEmisorGoogle.isNullOrEmpty()){
            nombreEmisorGoogle = nombrePerfilFirebase.toString()
            fotoUsGoogle = fotoPerfilFirebase.toString()
            subirComen.subirComent(nombreEmisorGoogle, idDocument.toString(), fotoUsGoogle, comentario)
            Log.e("NOMGOOGLE 1", nombreEmisorGoogle.toString())
            Log.e("FOTO GOOGLE 1", fotoUsGoogle.toString())

        }else{
            subirComen.subirComent(nombreEmisorGoogle, idDocument.toString(), fotoUsGoogle, comentario)
            Log.e("NOMGOOGLE 2", nombreEmisorGoogle.toString())
            Log.e("FOTO GOOGLE 2", fotoUsGoogle.toString())

        }

        Log.e("ID TRAB", IDUSUARIO.toString())
        snap()
    }
    private lateinit var db: FirebaseFirestore
    var arraySnap = ModeloCmentarios()
    fun snap(){
        db = FirebaseFirestore.getInstance()

        val docRef = db.collection("comentariosLikes")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                Log.e("SnapShot2 ", snapshot.documents.toString())
                for (snap in snapshot){

                    var comentariosActializados = snap.data
                    //arraySnap = comentariosActializados
                    observerComentData(arraySnap)

                    Log.e("SnapShot4 ", comentariosActializados.toString())


                }

            } else {
                Log.e("SnapShot 3 ", "ERROE DATA")
            }
        }
    }
}