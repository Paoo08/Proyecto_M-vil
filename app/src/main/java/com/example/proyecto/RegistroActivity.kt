package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegistroActivity : AppCompatActivity() {
    private lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener { registrarse() }



        setContentView(R.layout.activity_registro)


    }
    private fun registrarse() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}