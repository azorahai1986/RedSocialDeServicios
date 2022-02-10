package com.hernan.redsocialdeservicios.login

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.authRecibido
import com.hernan.redsocialdeservicios.databinding.ActivityLoginBinding
import com.hernan.redsocialdeservicios.onbtenerUser


class LoginActivity : AppCompatActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    var db = FirebaseFirestore.getInstance()
    lateinit var idDocument:String

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflarFragmetLogin()



    }

    private fun inflarFragmetLogin(){

        supportFragmentManager.beginTransaction().replace(R.id.loginContenedor, LoginFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

    }

    override fun onBackPressed() {

        val user = Firebase.auth.currentUser!!

        Log.e("AUTH in LoginFragment", user.toString())
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                }
            }

        idDocument()


        super.onBackPressed()
    }
    fun idDocument() {
        onbtenerUser()
        db.collection("DatosDeUsuarios").whereEqualTo("uid", authRecibido.toString()).get()
            .addOnSuccessListener { documents ->
                Log.e("id DOC1", authRecibido.toString())

                for (document in documents) {
                    idDocument = document.id

                    Log.e("id DOC 3", idDocument.toString())
                    db.collection("DatosDeUsuarios").document(idDocument)
                        .delete()
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }


                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


}