package com.hernan.redsocialdeservicios.login

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentCargarFotoPerfilBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CargarFotoPerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CargarFotoPerfilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCargarFotoPerfilBinding
    private val REQUEST_CAMERA = 1002
    private val REQUEST_IMAGE_CAPTURE = 1

    var foto: Uri? = null
    private var storageReference: StorageReference? = null
    val getUser = FirebaseAuth.getInstance().currentUser



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCargarFotoPerfilBinding.inflate(layoutInflater, container, false)

        abreCamara_click()
        //dispatchTakePictureIntent()
        binding.btTomarfoto.setOnClickListener { uploadFile()}

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CargarFotoPerfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CargarFotoPerfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

   /* private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imagenFoto.setImageBitmap(imageBitmap)
            Log.e("FOTO_URI", imageBitmap.toString())
        }
    }*/
    fun abreCamara_click(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (requireContext().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                val permissionCamara = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissionCamara, REQUEST_CAMERA)
            }else{
                abreCamara()
            }
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

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA){

            val path = data?.data
            Log.e("FOTO_PATH", path.toString())


            binding.imagenFoto.setImageURI(foto)
        }
    }
    private fun uploadFile() {
        //E/filePath: content://com.android.providers.media.documents/document/image%3A52609
        Log.e("FOTO_URI", foto.toString())
        //2021-07-14 13:32:03.697 3527-3527/com.example.navdrawer E/imageRef: gs://indumentaria-fd07b.appspot.com/images/4d00a1f4-27f7-4df7-b80d-cb7719bbb849

        if (foto != null) {
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setTitle("Cargando...")
            progressDialog.show()

            // para modificar los datos de una lista usando firestore..........................

            val imageRef = storageReference?.child("images/" + UUID.randomUUID().toString())
            Log.e("imageRef", imageRef.toString())


            var uploadTask = imageRef?.putFile(foto!!)
            val urlTask = uploadTask?.continueWithTask { task ->
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
                    var uid = getUser?.uid.toString()
                    var map = mutableMapOf<String, Any>()

                    map["imagen"] = imagen
                    map["uid"] = uid




                    FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
                        .document().set(map)
                    activity?.finish()
                } else {
                    // Handle failures
                    Toast.makeText(requireContext(), "Nuullll", Toast.LENGTH_SHORT).show()

                    // ...
                }
            }

        }else{
            Toast.makeText(requireContext(), "Nuullll", Toast.LENGTH_SHORT).show()

        }


    }

}