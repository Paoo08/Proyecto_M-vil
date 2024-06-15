package com.example.proyecto

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

    fun saveReservation(reservation: Reservation) {
        val editor = preferences.edit()
        editor.putString("hotel", reservation.hotel)
        editor.putString("checkInDate", reservation.checkInDate)
        editor.putString("checkOutDate", reservation.checkOutDate)
        editor.putString("room", reservation.room)
        editor.putString("persons", reservation.persons)
        editor.putString("beds", reservation.beds)
        editor.apply()
    }

    fun getReservation(): Reservation? {
        val hotel = preferences.getString("hotel", null) ?: return null
        val checkInDate = preferences.getString("checkInDate", null) ?: return null
        val checkOutDate = preferences.getString("checkOutDate", null) ?: return null
        val room = preferences.getString("room", null) ?: return null
        val persons = preferences.getString("persons", null) ?: return null
        val beds = preferences.getString("beds", null) ?: return null

        return Reservation(hotel, checkInDate, checkOutDate, room, persons, beds)
    }

    fun clearReservation() {
        preferences.edit().clear().apply()
    }
}