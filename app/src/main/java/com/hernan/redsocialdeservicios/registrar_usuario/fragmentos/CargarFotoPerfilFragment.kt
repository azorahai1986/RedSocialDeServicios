package com.hernan.redsocialdeservicios.registrar_usuario.fragmentos

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.hernan.redsocialdeservicios.authRecibido
import com.hernan.redsocialdeservicios.clases.dataFirebase
import com.hernan.redsocialdeservicios.clases.nombrePerfilFirebase
import com.hernan.redsocialdeservicios.databinding.FragmentCargarFotoPerfilBinding
import com.hernan.redsocialdeservicios.murogeneral.MuroGeneralActivity
import com.hernan.redsocialdeservicios.onbtenerUser
import java.util.*


class CargarFotoPerfilFragment : Fragment() {

    private lateinit var binding: FragmentCargarFotoPerfilBinding
    private val REQUEST_CAMERA = 1002
    private val PICK_IMAGE_REQUEST = 1234

    var foto: Uri? = null

    private var storageReference: StorageReference? = null
    lateinit var storage: FirebaseStorage
    var db = FirebaseFirestore.getInstance()
    private val getUser = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCargarFotoPerfilBinding.inflate(layoutInflater, container, false)
        storage = Firebase.storage
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference


        //db = FirebaseFirestore.getInstance()
        Toast.makeText(context, "toca la imagen para acceder a la galeria", Toast.LENGTH_LONG).show()
        binding.imagenFoto.setOnClickListener {abreCamara_click()}
        binding.btCargarFoto.setOnClickListener {uploadFile() }
        binding.imagenGaleria.setOnClickListener { showFilerChooser() }

        return binding.root
    }

    fun abreCamara_click(){
        if (requireContext().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
            requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

            val permissionCamara = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permissionCamara, REQUEST_CAMERA)
        }else{
            abreCamara()
        }
    }

    private fun abreCamara(){

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Nueva Imagen")
        foto = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto)
        startActivityForResult(camaraIntent, REQUEST_CAMERA)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA ->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    abreCamara()
                else
                    Toast.makeText(requireContext(), "No tienes acceso a la camara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {

            binding.imagenGaleria.setImageURI(foto)
        }
        if (data?.data != null){
            val filePathGaleria = data!!.data
            foto = filePathGaleria
            binding.imagenGaleria.setImageURI(foto)

        }

    }

    fun uploadFile() {
        onbtenerUser()
        Log.e("AUTH RECIBIDO FOTO", authRecibido.toString())
        var idDoc = ""

        if (foto != null) {
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setTitle("Cargando...")
            progressDialog.show()


            db.collection("DatosDeUsuarios").whereEqualTo("uid", authRecibido.toString()).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        idDoc = document.id
                        Log.e("id DOC", idDoc.toString())


                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
            // para modificar los datos de una lista usando firestore..........................
            val imageRef = storageReference!!.child("images/"+UUID.randomUUID().toString())
            Log.e("imageRef", imageRef.toString())

            var uploadTask = imageRef.putFile(foto!!)
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef?.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.e("downloadURL", downloadUri.toString())


                    var imagen = downloadUri.toString()


                    var map = mutableMapOf<String, Any>()

                    map["fotoUsuario"] = imagen

                   val enviarFotoFirebase = FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
                        .document(idDoc)
                    enviarFotoFirebase.update(map)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Usuario registrado con exito", Toast.LENGTH_SHORT) .show()
                            val intent = Intent(context, MuroGeneralActivity::class.java)
                            startActivity(intent)

                            progressDialog.dismiss()
                            activity?.finish()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Falló Modificación", Toast.LENGTH_SHORT).show()

                        }

                }


            }

        }


    }

    private fun showFilerChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }



}