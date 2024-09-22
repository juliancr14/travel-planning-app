package com.ud.parcialapp
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

class TripFormActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_form)

        val startDatePicker = findViewById<DatePicker>(R.id.startDatePicker)
        val endDatePicker = findViewById<DatePicker>(R.id.endDatePicker)
        val destinationEditText = findViewById<EditText>(R.id.destinationEditText)
        val createTripButton = findViewById<Button>(R.id.createTripButton)

        createTripButton.setOnClickListener {
            val startDate = "${startDatePicker.year}-${startDatePicker.month + 1}-${startDatePicker.dayOfMonth}"
            val endDate = "${endDatePicker.year}-${endDatePicker.month + 1}-${endDatePicker.dayOfMonth}"
            val destination = destinationEditText.text.toString()

            // Aquí puedes manejar la lógica de creación del viaje
            Toast.makeText(this, "Viaje a $destination del $startDate al $endDate creado", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad
        }
    }
}
