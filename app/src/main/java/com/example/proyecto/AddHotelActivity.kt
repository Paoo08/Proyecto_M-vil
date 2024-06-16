package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddHotelActivity : AppCompatActivity() {
    private lateinit var edtHotelName: EditText
    private lateinit var edtHotelLocation: EditText
    private lateinit var btnSaveHotel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)

        edtHotelName = findViewById(R.id.edtHotelName)
        edtHotelLocation = findViewById(R.id.edtHotelLocation)
        btnSaveHotel = findViewById(R.id.btnSaveHotel)

        btnSaveHotel.setOnClickListener {
            val hotelName = edtHotelName.text.toString()
            val hotelLocation = edtHotelLocation.text.toString()

            if (hotelName.isNotEmpty() && hotelLocation.isNotEmpty()) {
                // Lógica para guardar el hotel en la base de datos
                Toast.makeText(this, "Hotel guardado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
