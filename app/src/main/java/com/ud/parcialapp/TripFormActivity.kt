package com.ud.parcialapp

import Trip
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

class TripFormActivity : ComponentActivity() {

    // Inicializamos las vistas que se utilizarán en la actividad
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var destinationEditText: EditText
    private lateinit var createTripButton: Button

    // Fechas de inicio y fin del viaje
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_form)

        // Inicializar vistas
        startDateButton = findViewById(R.id.toggleStartDateButton)
        endDateButton = findViewById(R.id.toggleEndDateButton)
        destinationEditText = findViewById(R.id.destinationEditText)
        createTripButton = findViewById(R.id.createTripButton)

        // Configurar el botón para seleccionar la fecha de inicio
        startDateButton.setOnClickListener {
            showDatePicker { year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                // Validar que la fecha seleccionada no sea anterior a hoy
                if (selectedDate.isBefore(LocalDate.now())) {
                    Toast.makeText(this, "La fecha de inicio no puede ser anterior a hoy.", Toast.LENGTH_SHORT).show()
                } else {
                    startDate = selectedDate
                    startDateButton.text = "Fecha de Inicio: $startDate"
                    Log.d("TripFormActivity", "Fecha de Inicio seleccionada: $startDate")
                }
            }
        }

        // Configurar el botón para seleccionar la fecha de fin
        endDateButton.setOnClickListener {
            showDatePicker { year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                // Validar que la fecha de fin sea igual o posterior a la fecha de inicio
                if (startDate != null && selectedDate.isBefore(startDate)) {
                    Toast.makeText(this, "La fecha de fin debe ser igual o posterior a la fecha de inicio.", Toast.LENGTH_SHORT).show()
                } else {
                    endDate = selectedDate
                    endDateButton.text = "Fecha de Fin: $endDate"
                    Log.d("TripFormActivity", "Fecha de Fin seleccionada: $endDate")
                }
            }
        }

        // Configurar el botón para crear un viaje
        createTripButton.setOnClickListener {
            val destination = destinationEditText.text.toString()

            // Validar que todos los campos estén completos
            if (startDate != null && endDate != null && destination.isNotEmpty()) {
                val trip = Trip(
                    id = (1..100).random(), // ID aleatorio para el viaje
                    startDate = startDate!!,
                    endDate = endDate!!,
                    destination = destination
                )
                // Log de la información del viaje creado
                Log.d("TripFormActivity", "Salida: $startDate, Regreso: $endDate, Destino: $destination, Viaje creado: ${trip.id}")

                // Devolver el objeto Trip a la actividad principal
                val resultIntent = Intent()
                resultIntent.putExtra("trip", trip)
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Cerrar la actividad
            } else {
                Log.d("TripFormActivity", "Datos incompletos: $startDate, $endDate, $destination")
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para mostrar un DatePicker y devolver los valores seleccionados
    private fun showDatePicker(onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crear y mostrar el DatePicker
        DatePickerDialog(this, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
            Log.d("TripFormActivity", "Fecha seleccionada: $selectedYear-${selectedMonth + 1}-$selectedDay")
        }, year, month, day).show()
    }
}
