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
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var destinationEditText: EditText
    private lateinit var createTripButton: Button

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null
    private var tripToEdit: Trip? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_form)

        // Inicializar vistas
        startDateButton = findViewById(R.id.toggleStartDateButton)
        endDateButton = findViewById(R.id.toggleEndDateButton)
        destinationEditText = findViewById(R.id.destinationEditText)
        createTripButton = findViewById(R.id.createTripButton)

        // Revisar si estamos en modo de edición
        tripToEdit = intent.getSerializableExtra("trip") as? Trip
        tripToEdit?.let {
            // Si estamos editando un viaje, rellenamos los campos con los datos existentes
            startDate = it.startDate
            endDate = it.endDate
            destinationEditText.setText(it.destination)

            startDateButton.text = "Fecha de Inicio: $startDate"
            endDateButton.text = "Fecha de Fin: $endDate"
            createTripButton.text = "Actualizar Viaje"  // Cambiamos el texto del botón
        }

        // Configurar botón para seleccionar fecha de inicio
        startDateButton.setOnClickListener {
            showDatePicker { year, month, dayOfMonth ->
                startDate = LocalDate.of(year, month + 1, dayOfMonth) // month es 0-based, por eso sumamos 1
                startDateButton.text = "Fecha de Inicio: $startDate"
                Log.d("TripFormActivity", "Fecha de Inicio seleccionada: $startDate") // Log de la fecha de inicio

            }
        }

        // Configurar botón para seleccionar fecha de fin
        endDateButton.setOnClickListener {
            showDatePicker { year, month, dayOfMonth ->
                endDate = LocalDate.of(year, month + 1, dayOfMonth)
                endDateButton.text = "Fecha de Fin: $endDate"
                Log.d("TripFormActivity", "Fecha de Fin seleccionada: $endDate") // Log de la fecha de fin
            }
        }

        // Configurar el botón para crear o actualizar un viaje
        createTripButton.setOnClickListener {
            val destination = destinationEditText.text.toString()

            // Validar que los campos estén completos
            if (startDate != null && endDate != null && destination.isNotEmpty()) {
                val trip = tripToEdit?.editTrip(startDate!!, endDate!!, destination)
                    ?: Trip(
                        id = (1..100).random(),
                        startDate = startDate!!,
                        endDate = endDate!!,
                        destination = destination
                    )

                // Log de edición o creación
                Log.d("TripFormActivity", "salida: $startDate")
                Log.d("TripFormActivity", "regreso: $endDate")
                Log.d("TripFormActivity", "Destino: $destination")
                Log.d("TripFormActivity", "Viaje ${if (tripToEdit != null) "actualizado" else "creado"}: ${trip.id}")

                // Devolver el objeto Trip actualizado o creado a la actividad principal
                val resultIntent = Intent().apply {
                    putExtra("trip", trip)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Log.d("TripFormActivity", "Datos incompletos: $startDate, $endDate, $destination") // Log de error
                // Aquí puedes usar un Toast o un SnackBar
            }
        }
    }

    // Método para mostrar un DatePicker y devolver los valores seleccionados
    private fun showDatePicker(onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
            Log.d("TripFormActivity", "Fecha seleccionada: $selectedYear-${selectedMonth + 1}-$selectedDay")
        }, year, month, day).show()
    }
}
