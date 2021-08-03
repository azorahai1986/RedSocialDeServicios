package com.hernan.redsocialdeservicios.trabajosdeusuario

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.hernan.redsocialdeservicios.clases.dataFirebase
import com.hernan.redsocialdeservicios.clases.fotoPerfilFirebase
import com.hernan.redsocialdeservicios.databinding.FragmentCargarTrabajoBinding
import java.util.*


class CargarTrabajoFragment : Fragment() {

    private var arrayImagePath = arrayListOf<Uri>()
    private var imagenUs:String = ""
    private var arrayURLs = arrayListOf<String>()
    lateinit var storage: FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1234
    val getUser = FirebaseAuth.getInstance().currentUser
    private var storageReference: StorageReference? = null
    private var adapterImagenes: AdapterViewPagerTrabajos? = null

    private lateinit var binding: FragmentCargarTrabajoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCargarTrabajoBinding.inflate(layoutInflater, container, false)
        storage = Firebase.storage

        // dare init a firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        adapterImagenes = AdapterViewPagerTrabajos(arrayListOf())

        dataFirebase()


        showFilerChooser()
        binding.btPublicar.setOnClickListener { uploadFile()
        }


        return binding.root
    }

    private fun uploadImage(pos: Int){

        if(pos == arrayImagePath.size) {


            var id = getUser?.uid.toString()
            var arrayIm = arrayURLs
            var imagenPrincipal = if(arrayURLs.isEmpty()) "" else arrayURLs[0]
            var enunciado = binding.etCargarTrabajo.text.toString()
            imagenUs = fotoPerfilFirebase


            var map = mutableMapOf<String, Any>()

            map["id"] = id
            map["enunciado"] = enunciado
            map["imagenUsuario"] = imagenUs
            map["arrayImagen"] = arrayIm
            map["imagenPrincipal"] = imagenPrincipal

            FirebaseFirestore.getInstance().collection("TrabajosDelUsusario")
                .document().set(map)
            activity?.finish()

        }else{

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())

            var uploadTask = imageRef.putFile(arrayImagePath[pos])
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    arrayURLs.add(downloadUri.toString())
                    uploadImage(pos + 1)

                    // para editar los datos de la lista...........................................





                } else {
                    Toast.makeText(context, "Error al subir", Toast.LENGTH_SHORT).show()
                    // Handle failures
                    // ...
                }
            }

        }



    }
    private fun uploadFile() {

        if (arrayImagePath.isNotEmpty()) {
            Log.e("arrayImagen_ upload2", arrayImagePath.toString())

            val progressDialog = ProgressDialog(context)

            progressDialog.setTitle("Cargando...")
            progressDialog.show()

            uploadImage(0)

        }else {
            Toast.makeText(context, "Agreque imagenes", Toast.LENGTH_SHORT).show()
        }



    }


    /**
     * abre una nueva activity(externa de otra app nativa)
     * para abrir y seleccionar imagenes
     */
    private fun showFilerChooser() {
        val intent = Intent()
        intent.type = "image/*"
        /**
         * Intent.EXTRA_ALLOW_MULTIPLE
         * Sirve para seleccionar mas de una imagen
         * de una vez
         */
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Seleccione las imagenes"), PICK_IMAGE_REQUEST)
    }

    /**
     * Uri es una direccion de donde se encuentra la imagen
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            if(data.clipData!=null){
                for(i in 0 until data.clipData!!.itemCount){
                    val uriAux = data.clipData!!.getItemAt(i).uri
                    arrayImagePath.add(uriAux)

                    Log.e("arrayImagen1", arrayImagePath.toString())
                }
            }else {
                if(data.data!=null){
                    val uriA = data.data!!
                    arrayImagePath.add(uriA)

                }
            }
            binding.PagerCargarTrabajo.adapter = adapterImagenes
            adapterImagenes?.arrayImagenes = arrayImagePath
            adapterImagenes?.notifyDataSetChanged()
            //adapterImagenes = AdapterImagenCrear(, this)


            /**
             * llenar datos en el viewpager para mostrarlos
             * antes de subirlos
             * ejemplo
             * adapterViewPager.arrayPath = arrayImagePath
             * adaoterViewPager.notifySetDataChanged()
             */
            /*filePath = data.data
            try {

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                /**
                 * mostrar las imagenes
                 */
                // imageView_produ!!.setImageBitmap(bitmap)


            } catch (e: IOException) {
                e.printStackTrace()
            }*/
        }

    }


}