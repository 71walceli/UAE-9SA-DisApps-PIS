package com.example.a20231211_taller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a20231211_taller.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val DEFAULT_USERNAME = "admin"
    private val DEFAULT_PASSWORD = "p4s5w0rD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener{
            if (binding.username.text.toString() == DEFAULT_USERNAME
                && binding.password.text.toString() == DEFAULT_PASSWORD
            ) {
                binding.statusText.setText("Inicio de Sesion Correcto")
                binding.statusText.setTextColor(Color.GREEN)
                startActivity(Intent(this, MenuActivity::class.java))
            } else {
                binding.statusText.setText("Revise sus credenciales")
                binding.statusText.setTextColor(Color.RED)
            }
        }

        if (BuildConfig.DEBUG) {
            startActivity(Intent(this, MenuActivity::class.java))
            Toast.makeText(this, "Saltando por modo Desarrllador", Toast.LENGTH_SHORT)
        }
    }
}