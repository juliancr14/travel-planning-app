package com.ud.parcialapp

import Trip
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.parcialapp.ui.theme.ParcialAPPTheme
import java.time.LocalDate
import android.content.Intent
import androidx.compose.material3.AlertDialog

class TripDetailsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParcialAPPTheme {
                // Obtener el objeto Trip pasado a través del Intent
                val trip = intent.getSerializableExtra("trip") as Trip
                // Mostrar la pantalla de detalles del viaje
                TripDetailsScreen(trip = trip) { tripId ->
                    // Devolver el ID del viaje eliminado al MainActivity
                    val resultIntent = Intent().apply {
                        putExtra("deleted_trip_id", tripId)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish() // Cerrar la actividad
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripDetailsScreen(trip: Trip, onTripDeleted: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) } // Estado para controlar el diálogo de eliminación

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Detalles del Viaje", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Destino: ${trip.destination.uppercase()}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Fecha inicio: ${trip.startDate}")
        Text(text = "Fecha fin: ${trip.endDate}")
        Text(text = "Duración: ${trip.tripDuration()} días")
        Spacer(modifier = Modifier.height(16.dp))

        // Fila con botones para editar y eliminar el viaje
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { /* Lógica para editar viaje */ }) {
                Text(text = "Editar")
            }
            Button(onClick = { showDialog = true }) {
                Text(text = "Eliminar")
            }
        }
    }

    // Mostrar un diálogo de confirmación si showDialog es verdadero
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación") },
            text = { Text("¿Está seguro de eliminar el viaje?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onTripDeleted(trip.id) // Llama a la función de eliminación
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TripDetailsScreenPreview() {
    ParcialAPPTheme {
        // Vista previa de la pantalla de detalles del viaje
        TripDetailsScreen(trip = Trip(id = 1, startDate = LocalDate.now(), endDate = LocalDate.now().plusDays(5), destination = "Ciudad Ejemplo")) { }
    }
}
