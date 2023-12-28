package com.example.a20231211_taller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    public fun formEmpleados(v: View) {
        startActivity(Intent(this, FormEmpleados::class.java))
    }

    fun reconooncer_inflorescencias__onClick(view: View) {
        startActivity(Intent(this, InflorescenciasActivity::class.java))
    }
}