package com.example.proyecto

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(correo: String, contrasena: String) {
        val editor = sharedPreferences.edit()
        editor.putString(correo, contrasena)
        editor.apply()
    }

    fun getUserPassword(correo: String): String? {
        return sharedPreferences.getString(correo, null)
    }
}
