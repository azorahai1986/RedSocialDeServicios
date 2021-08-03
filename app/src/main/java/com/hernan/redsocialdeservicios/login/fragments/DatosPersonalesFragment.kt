package com.hernan.redsocialdeservicios.login.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.hernan.redsocialdeservicios.R
import com.hernan.redsocialdeservicios.databinding.FragmentDatosPersonalesBinding
import com.hernan.redsocialdeservicios.login.EnviarDatosALaNube
import com.hernan.redsocialdeservicios.trabajosdeusuario.TrabajosDelUsuarioActivity
import java.text.SimpleDateFormat
import java.util.*


class DatosPersonalesFragment : Fragment() {

    var formatDate = SimpleDateFormat("dd MM YYYY", Locale.US)

    lateinit var enviarDatos: EnviarDatosALaNube
    private var nombreYApellido: String? = null


    val getUser = FirebaseAuth.getInstance().currentUser


    private lateinit var binding: FragmentDatosPersonalesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatosPersonalesBinding.inflate(inflater, container, false)

        enviarDatos = EnviarDatosALaNube()
        asociarInterface()

        binding.btFinalizar.setOnClickListener {
            cargarDatosUsuarioFirebase()
            irAlSiguienteActivity()}

        return binding.root
    }

    private fun asociarInterface(){

        binding.imagenCalendario.setOnClickListener { calendario() }
        binding.textEmailRegistrado.text = getUser?.email.toString()
        Glide.with(requireContext().applicationContext).load(getUser?.photoUrl.toString()).into(binding.ImagenRegistrarse)

        when{
            !getUser?.displayName.isNullOrEmpty()->{
                binding.textNombreRegistrado.text = getUser?.displayName.toString()
                nombreYApellido = binding.textNombreRegistrado.text.toString()
                binding.inputLayoutNombreRegistrar.visibility = View.INVISIBLE
                binding.textNombreRegistrado.visibility = View.VISIBLE
                binding.inputLayoutApellidoRegistrar.visibility = View.INVISIBLE
                Log.e("Nombre y Apellido", nombreYApellido.toString())

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

            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    fun irAlSiguienteActivity() {
        Log.e("PHOTO URL", getUser?.displayName.toString())

        when {

            getUser?.displayName.isNullOrEmpty() -> {
                val cargarFotoUsuario = CargarFotoPerfilFragment()

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.loginContenedor, cargarFotoUsuario)
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
            }
            !getUser?.displayName.isNullOrEmpty() -> {
                val intent = Intent(context, TrabajosDelUsuarioActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }




    }


    fun cargarDatosUsuarioFirebase(){
        Log.e("Nombre y Apellido2", nombreYApellido.toString())
        enviarDatos.cargarDatosPersonales(getUser?.email.toString(), getUser?.uid.toString(),
            nombreYApellido.toString(), binding.etNombreRegistrar.text.toString(),
            binding.etApellidoRegistrar.text.toString(), getUser?.photoUrl.toString(),
            binding.textFechaDeNac.text.toString())
    }


}