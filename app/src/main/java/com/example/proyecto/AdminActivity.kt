package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AdminActivity : AppCompatActivity() {
    private lateinit var btnAddHotel: Button
    private lateinit var btnEditHotel: Button
    private lateinit var btnDeleteHotel: Button
    private lateinit var btnAddUser: Button
    private lateinit var btnEditUser: Button
    private lateinit var btnDeleteUser: Button
    private lateinit var btnManageReservations: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btnAddHotel = findViewById(R.id.btnAddHotel)
        btnEditHotel = findViewById(R.id.btnEditHotel)
        btnDeleteHotel = findViewById(R.id.btnDeleteHotel)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnEditUser = findViewById(R.id.btnEditUser)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)
        btnManageReservations = findViewById(R.id.btnManageReservations)

        btnAddHotel.setOnClickListener {
            val intent = Intent(this, AddHotelActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_HOTEL)
        }

        // Otras configuraciones de botones
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    companion object {
        private const val REQUEST_CODE_ADD_HOTEL = 1
    }
}
