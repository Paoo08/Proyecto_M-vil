package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    private lateinit var edtNombre: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtNumero: EditText
    private lateinit var edtContra: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        edtNombre = findViewById(R.id.edtNombre)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtNumero = findViewById(R.id.edtNumero)
        edtContra = findViewById(R.id.edtContra)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        sharedPrefManager = SharedPrefManager(this)

        btnRegistrar.setOnClickListener { registrarse() }
    }

    private fun registrarse() {
        val correo = edtCorreo.text.toString()
        val contrasena = edtContra.text.toString()

        if (correo.isNotEmpty() && contrasena.isNotEmpty()) {
            sharedPrefManager.saveUser(correo, contrasena)
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
