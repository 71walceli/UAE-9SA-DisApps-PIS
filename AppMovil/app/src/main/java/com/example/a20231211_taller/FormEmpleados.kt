package com.example.a20231211_taller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class FormEmpleados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_empleados)

        val select_rol = findViewById<Spinner>(R.id.select_rol);
        val roles =  ArrayList<String>();

        roles.add("--Elija un rol--");
        roles.add("Administrador")
        roles.add("Empleado")
        roles.add("Ingeniero agronomo")
        val roles_usuario = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        roles_usuario.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        select_rol.setAdapter(roles_usuario)
    }
}