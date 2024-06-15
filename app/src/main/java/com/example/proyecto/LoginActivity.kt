package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.databinding.FragmentHomeBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var edtCorreo: EditText
    private lateinit var edtContra: EditText
    private lateinit var btnIngresar: Button
    private lateinit var btnRegistrarse: Button

    // Lista de administradores predeterminados
    private val administradores = listOf(
        Admin("admin1@admin.com", "admin123"),
        Admin("admin2@admin.com", "admin123"),
        Admin("admin3@admin.com", "admin123"),
        Admin("admin4@admin.com", "admin123"),
        Admin("admin5@admin.com", "admin123")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtCorreo = findViewById(R.id.edtCorreo)
        edtContra = findViewById(R.id.edtContra)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)

        btnIngresar.setOnClickListener { ingresar() }
        btnRegistrarse.setOnClickListener { registrarse() }
    }

    private fun ingresar() {
        val correo = edtCorreo.text.toString()
        val contrasena = edtContra.text.toString()

        val adminEncontrado = administradores.find { it.correo == correo && it.contrasena == contrasena }

        if (adminEncontrado != null) {
            // Usuario es administrador
            Toast.makeText(this, "Bienvenido Admin", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // Aquí puedes añadir la lógica para verificar si el usuario es un usuario normal
            // Por ahora, mostramos un mensaje de error
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarse() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}