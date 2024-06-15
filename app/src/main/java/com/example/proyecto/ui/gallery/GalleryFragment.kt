package com.example.proyecto.ui.gallery

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyecto.R
import com.example.proyecto.Reservation
import com.example.proyecto.databinding.FragmentGalleryBinding

import java.util.*

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val reservations = ArrayList<Reservation>(20)
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val hotelSpinner = binding.hotelSpinner
        val checkInDate = binding.checkInDate
        val checkOutDate = binding.checkOutDate
        val roomSpinner = binding.roomSpinner
        val personSpinner = binding.personSpinner
        val bedSpinner = binding.bedSpinner
        val hotelInfo = binding.hotelInfo
        val hotelImage = binding.hotelImage
        val reserveButton = binding.reserveButton

        // Configure spinners
        ArrayAdapter.createFromResource(
            requireContext(), R.array.hotels_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hotelSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.rooms_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            roomSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.persons_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            personSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(), R.array.beds_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bedSpinner.adapter = adapter
        }

        // Set date pickers
        checkInDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    checkInDate.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                }, year, month, day
            )
            datePicker.show()
        }

        checkOutDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    checkOutDate.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                }, year, month, day
            )
            datePicker.show()
        }

        // Show hotel info and image based on selection
        hotelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val hotelDescriptions = resources.getStringArray(R.array.hotels_info_array)
                hotelInfo.text = hotelDescriptions[position]

                val hotelImages = intArrayOf(R.drawable.hotel_a, R.drawable.hotel_b, R.drawable.hotel_c)
                hotelImage.setImageResource(hotelImages[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                hotelInfo.text = ""
                hotelImage.setImageResource(0)
            }
        }

        // Handle reservation button click
        reserveButton.setOnClickListener {
            val hotel = hotelSpinner.selectedItem.toString()
            val checkIn = checkInDate.text.toString()
            val checkOut = checkOutDate.text.toString()
            val room = roomSpinner.selectedItem.toString()
            val persons = personSpinner.selectedItem.toString()
            val beds = bedSpinner.selectedItem.toString()

            if (reservations.size < 20) {
                val reservation = Reservation(hotel, checkIn, checkOut, room, persons, beds)
                reservations.add(reservation)
                Toast.makeText(activity, "Reservación guardada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Límite de reservaciones alcanzado", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}