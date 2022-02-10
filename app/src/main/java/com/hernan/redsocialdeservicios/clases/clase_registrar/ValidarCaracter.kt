package com.hernan.redsocialdeservicios.clases.clase_registrar

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import androidx.core.util.PatternsCompat
import androidx.fragment.app.Fragment
import java.util.regex.Pattern


fun EditText.validater(validation: (String) -> Unit){
    this.addTextChangedListener(object :TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            validation(p0.toString())

        }

    })


}
fun Fragment.isValidEmail(email:String):Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()


}
fun Fragment.isValidPassword(password:String):Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()


}
