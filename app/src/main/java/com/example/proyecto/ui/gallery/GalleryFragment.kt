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
import android.app.AlertDialog
import com.example.proyecto.databinding.FragmentGalleryBinding
import com.example.proyecto.SessionManager

import java.util.*

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val reservations = ArrayList<Reservation>(20)
    private val calendar = Calendar.getInstance()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sessionManager = SessionManager(requireContext())

        val hotelSpinner = binding.hotelSpinner
        val checkInDate = binding.checkInDate
        val checkOutDate = binding.checkOutDate
        val roomSpinner = binding.roomSpinner
        val personSpinner = binding.personSpinner
        val bedSpinner = binding.bedSpinner
        val hotelInfo = binding.hotelInfo
        val hotelImage = binding.hotelImage
        val reserveButton = binding.reserveButton
        val viewReservationButton = binding.viewReservationButton
        val modifyReservationButton = binding.modifyReservationButton
        val deleteReservationButton = binding.deleteReservationButton

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
            val reservation = Reservation(
                hotelSpinner.selectedItem.toString(),
                checkInDate.text.toString(),
                checkOutDate.text.toString(),
                roomSpinner.selectedItem.toString(),
                personSpinner.selectedItem.toString(),
                bedSpinner.selectedItem.toString()
            )
            if (reservations.size < 20) {
                reservations.add(reservation)
                sessionManager.saveReservation(reservation)
                Toast.makeText(activity, "Reservación guardada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "No se pueden guardar más de 20 reservaciones", Toast.LENGTH_SHORT).show()
            }
        }

        // View reservation button click
        viewReservationButton.setOnClickListener {
            val reservation = sessionManager.getReservation()
            reservation?.let {
                Toast.makeText(
                    activity, "Reserva actual: Hotel ${it.hotel}, Check-in ${it.checkInDate}, Check-out ${it.checkOutDate}", Toast.LENGTH_LONG
                ).show()
            } ?: run {
                Toast.makeText(activity, "No hay reservaciones guardadas", Toast.LENGTH_SHORT).show()
            }
        }

        // Modify reservation button click
        modifyReservationButton.setOnClickListener {
            val reservation = sessionManager.getReservation()
            reservation?.let {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("¿Estás seguro que quieres modificar esta reservación?")
                    .setPositiveButton("Sí") { _, _ ->
                        val modifiedReservation = Reservation(
                            hotelSpinner.selectedItem.toString(),
                            checkInDate.text.toString(),
                            checkOutDate.text.toString(),
                            roomSpinner.selectedItem.toString(),
                            personSpinner.selectedItem.toString(),
                            bedSpinner.selectedItem.toString()
                        )
                        reservations[reservations.indexOf(it)] = modifiedReservation
                        sessionManager.saveReservation(modifiedReservation)
                        Toast.makeText(activity, "Reservación modificada", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                builder.create().show()
            } ?: run {
                Toast.makeText(activity, "No hay reservaciones guardadas para modificar", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete reservation button click
        deleteReservationButton.setOnClickListener {
            val reservation = sessionManager.getReservation()
            reservation?.let {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("¿Estás seguro que quieres eliminar esta reservación?")
                    .setPositiveButton("Sí") { _, _ ->
                        reservations.remove(it)
                        sessionManager.clearReservation()
                        Toast.makeText(activity, "Reservación eliminada", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                builder.create().show()
            } ?: run {
                Toast.makeText(activity, "No hay reservaciones guardadas para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}