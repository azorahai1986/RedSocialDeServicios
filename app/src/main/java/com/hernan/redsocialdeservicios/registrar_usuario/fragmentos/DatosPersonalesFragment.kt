package com.hernan.redsocialdeservicios.registrar_usuario.fragmentos

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hernan.redsocialdeservicios.*
import com.hernan.redsocialdeservicios.databinding.FragmentDatosPersonalesBinding
import com.hernan.redsocialdeservicios.login.EnviarDatosALaNube

import com.hernan.redsocialdeservicios.murogeneral.MuroGeneralActivity
import java.text.SimpleDateFormat
import java.util.*

private const val EMAIL = "email"
private const val PASWORD = "pasword"
class DatosPersonalesFragment : Fragment() {
    private var email: String? = null
    private var pasword: String? = null
    private var edad: String? = null
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val getUser = FirebaseAuth.getInstance().currentUser
    var uidRecuperado:String? = null
    var nombreRecuperado:String? = null
    var emailRecuperado:String? = null
    var fotoRecuperado:String? = null


    var formatDate = SimpleDateFormat("dd MM YYYY", Locale.US)
    private var nombreYApellido: String? = null
    private lateinit var binding: FragmentDatosPersonalesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(EMAIL)
            pasword = it.getString(PASWORD)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatosPersonalesBinding.inflate(inflater, container, false)
        asociarInterface()


        binding.btFinalizar.setOnClickListener {
            validarEmail()
            //irAlSiguienteActivity()
        }


        return binding.root
    }

    private fun asociarInterface(){

        binding.imagenCalendario.setOnClickListener { calendario() }
        binding.textEmailRegistrado.text = email
        Glide.with(requireContext().applicationContext).load(getUser?.photoUrl.toString()).into(binding.ImagenRegistrarse)

        when{
            !getUser?.displayName.isNullOrEmpty()->{
                binding.textNombreRegistrado.text = getUser?.displayName.toString()
                nombreYApellido = binding.textNombreRegistrado.text.toString()
                binding.inputLayoutNombreRegistrar.visibility = View.INVISIBLE
                binding.textNombreRegistrado.visibility = View.VISIBLE
                binding.inputLayoutApellidoRegistrar.visibility = View.INVISIBLE

            }
            getUser?.displayName.isNullOrEmpty()->{
            binding.inputLayoutNombreRegistrar.visibility = View.VISIBLE
            binding.textNombreRegistrado.visibility = View.INVISIBLE
            binding.inputLayoutApellidoRegistrar.visibility = View.VISIBLE


            }

        }
    }

    fun calendario(){
        val getDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener{datePicker, i, i2, i3 ->

                val selecDate = Calendar.getInstance()
                selecDate.set(Calendar.YEAR, i)
                selecDate.set(Calendar.MONTH, i2)
                selecDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selecDate.time)
                binding.textFechaDeNac.text = date
                edad = date

            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }
    fun validarEmail(){
        auth.signInWithEmailAndPassword(email.toString(), pasword.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){

                if (auth.currentUser!!.isEmailVerified){
                    Toast.makeText(context, "email verificado", Toast.LENGTH_SHORT).show()
                    irAlSiguienteActivity()
                }
                else{
                    Toast.makeText(context, "debe verificar email desde la casilla de correo", Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(context, "email no se pudo verifiar", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun irAlSiguienteActivity() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            uidRecuperado = user.uid
            nombreRecuperado = user.displayName
            emailRecuperado = user.email
            fotoRecuperado = user.photoUrl?.toString()

        }

       // Log.e("AUTH DATOS PERS RECUPERADO", uidRecuperado.toString())

        val nombre = binding.etNombreRegistrar.text
        val apellido = binding.etApellidoRegistrar.text
        val nombreCompleto = "$nombre $apellido"
        when {

            nombreRecuperado.isNullOrEmpty() -> {
               // val edad = binding.textFechaDeNac.text.toString()
                var enviarDataUser = EnviarDatosALaNube()
                enviarDataUser.cargarDatosPersonales(
                    email.toString(),
                    nombreCompleto, edad.toString(), uidRecuperado.toString(), "")


                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.loginContenedor, CargarFotoPerfilFragment())
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
            }
            !nombreRecuperado.isNullOrEmpty() -> {
                var enviarDataUser = EnviarDatosALaNube()
                enviarDataUser.cargarDatosPersonales(emailRecuperado.toString(),
                    nombreRecuperado.toString(), edad.toString(), uidRecuperado.toString(), fotoRecuperado.toString())
                val intent = Intent(context, MuroGeneralActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }




    }

    companion object {

        fun newInstance(email: String, pasword: String) =
            DatosPersonalesFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)
                    putString(PASWORD, pasword)
                }
            }
    }




}