package com.hernan.redsocialdeservicios.login

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.hernan.redsocialdeservicios.databinding.FragmentDatosPersonalesBinding
import com.hernan.redsocialdeservicios.trabajosdeusuario.TrabajosDelUsuarioActivity
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EMAIL_RECIBIDO = "Email"
private const val UID_RECIBIDO = "Uid"


class DatosPersonalesFragment : Fragment() {

    var formatDate = SimpleDateFormat("dd MM YYYY", Locale.US)

    lateinit var enviarDatos:EnviarDatosALaNube
    private var emailRecibido: String? = null
    private var uidRecibido: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailRecibido = it.getString(EMAIL_RECIBIDO)
            uidRecibido = it.getString(UID_RECIBIDO)
        }
    }

    private lateinit var binding: FragmentDatosPersonalesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatosPersonalesBinding.inflate(inflater, container, false)

        enviarDatos = EnviarDatosALaNube()
        binding.imagenCalendario.setOnClickListener { calendario() }
        binding.textEmailRegistrado.text = emailRecibido

        binding.btFinalizar.setOnClickListener {

            enviarDatos.cargarDatosPersonales(emailRecibido.toString(), uidRecibido.toString(),
                binding.etNombreRegistrar.text.toString(), binding.etApellidoRegistrar.text.toString(),
                binding.etEdadRegistrar.text.toString(), binding.textFechaDeNac.text.toString()
            )
            irAlSiguienteActivity()

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(emailRecibido: String, uidRecibido: String) =
            DatosPersonalesFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_RECIBIDO, emailRecibido)
                    putString(UID_RECIBIDO, uidRecibido)
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
    fun cargarData() {
        var user = mutableMapOf<String, Any>()
        user["email"] = emailRecibido.toString()
        user["uid"] = uidRecibido.toString()
        user["nombre"] = binding.etNombreRegistrar.text.toString()
        user["apellido"] = binding.etApellidoRegistrar.text.toString()
        user["edad"] = binding.etEdadRegistrar.text.toString()
        user["nacimiento"] = binding.textFechaDeNac.text.toString()


        FirebaseFirestore.getInstance().collection("DatosDeUsuarios")
            .add(user).addOnSuccessListener{
                Toast.makeText(context, "exito", Toast.LENGTH_SHORT).show()

            }
    }
    fun irAlSiguienteActivity() {


        val intent = Intent(context, TrabajosDelUsuarioActivity::class.java)
                            intent.putExtra("EMAIL", emailRecibido)
                            intent.putExtra("IDUSUARIO", uidRecibido)
                            startActivity(intent)
                            activity?.finish()
    }
}