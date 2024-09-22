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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_form)

        // Inicializar vistas
        startDateButton = findViewById(R.id.toggleStartDateButton)
        endDateButton = findViewById(R.id.toggleEndDateButton)
        destinationEditText = findViewById(R.id.destinationEditText)
        createTripButton = findViewById(R.id.createTripButton)

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

        // Configurar el botón para crear un viaje
        createTripButton.setOnClickListener {
            val destination = destinationEditText.text.toString()

            // Validar que los campos estén completos
            if (startDate != null && endDate != null && destination.isNotEmpty()) {
                val trip = Trip(
                    id = (1..100).random(),
                    startDate = startDate!!,
                    endDate = endDate!!,
                    destination = destination
                )
                // Log del destino y el viaje creado
                Log.d("TripFormActivity", "salida: $startDate")
                Log.d("TripFormActivity", "regreso: $endDate")
                Log.d("TripFormActivity", "Destino: $destination")
                Log.d("TripFormActivity", "Viaje creado: ${trip.id}")

                // Devolver el objeto Trip a la actividad principal
                val resultIntent = Intent()
                resultIntent.putExtra("trip", trip)
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Cerrar la actividad
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
            Log.d("TripFormActivity", "Fecha seleccionada: $selectedYear-${selectedMonth + 1}-$selectedDay") // Log de la fecha seleccionada
        }, year, month, day).show()
    }
}
