package com.example.a20231211_taller

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class FormEmpleados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_empleados)

        val cedula_input = findViewById<EditText>(R.id.cedula)
        cedula_input.addTextChangedListener(object : TextValidator(cedula_input) {
            override fun validate(tv: TextView?, text: String?) {
                if (tv!!.text.toString().length != 10)
                    tv.error = "Cedula debe ser 10 digitos"
                else
                    tv.error = null
            }
        })

        val rol_select = findViewById<Spinner>(R.id.select_rol);
        val rol_items =  ArrayList<String>();
        rol_items.add("--Elija un rol--");
        rol_items.add("Administrador")
        rol_items.add("Empleado")
        rol_items.add("Ingeniero agronomo")
        val rol_adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rol_items);
        rol_adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        rol_select.setAdapter(rol_adapter)

        val f_nacimiento_cal = Calendar.getInstance()
        val f_nacimiento_input = findViewById<EditText>(R.id.f_nacimiento)
        val f_nacimiento_listener = OnDateSetListener { view, year, month, day ->
                f_nacimiento_cal.set(Calendar.YEAR, year)
                f_nacimiento_cal.set(Calendar.MONTH, month)
                f_nacimiento_cal.set(Calendar.DAY_OF_MONTH, day)

                val myFormat = "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat(myFormat, Locale.getDefault())
                f_nacimiento_input.setText(dateFormat.format(f_nacimiento_cal.getTime()))
            }
        f_nacimiento_input.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                this@FormEmpleados,
                f_nacimiento_listener,
                f_nacimiento_cal.get(Calendar.YEAR),
                f_nacimiento_cal.get(Calendar.MONTH),
                f_nacimiento_cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        })

        val f_ingreso_cal = Calendar.getInstance()
        val f_ingreso_input = findViewById<EditText>(R.id.f_ingreso)
        val f_ingreso_listener = OnDateSetListener { view, year, month, day ->
            f_ingreso_cal.set(Calendar.YEAR, year)
            f_ingreso_cal.set(Calendar.MONTH, month)
            f_ingreso_cal.set(Calendar.DAY_OF_MONTH, day)

            val myFormat = "dd/MM/yyyy"
            val dateFormat = SimpleDateFormat(myFormat, Locale.getDefault())
            f_ingreso_input.setText(dateFormat.format(f_ingreso_cal.getTime()))
        }
        f_ingreso_input.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                this@FormEmpleados,
                f_ingreso_listener,
                f_ingreso_cal.get(Calendar.YEAR),
                f_ingreso_cal.get(Calendar.MONTH),
                f_ingreso_cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        })

        val jornada_select = findViewById<Spinner>(R.id.jornada);
        val jornada_items =  ArrayList<String>();
        jornada_items.add("--Elija un rol--");
        jornada_items.add("8:00 - 5:00")
        jornada_items.add("8:00 - 12:00")
        jornada_items.add("13:00 - 5:00")
        val jornada_adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jornada_items);
        jornada_adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        jornada_select.setAdapter(jornada_adapter)
    }
}

abstract class TextValidator(private val textView: TextView) : TextWatcher {
    abstract fun validate(textView: TextView?, text: String?)
    override fun afterTextChanged(s: Editable) {
        val text = textView.text.toString()
        validate(textView, text)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        /* Needs to be implemented, but we are not using it. */
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        /* Needs to be implemented, but we are not using it. */
    }
}
