package com.ud.parcialapp

import Trip
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.parcialapp.ui.theme.ParcialAPPTheme
import java.time.LocalDate

class TripDetailsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParcialAPPTheme {
                TripDetailsScreen(trip = intent.getSerializableExtra("trip") as Trip)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripDetailsScreen(trip: Trip) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Detalles del Viaje", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Destino: ${trip.destination.uppercase()}", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Fecha inicio: ${trip.startDate}")
        Text(text = "Fecha fin: ${trip.endDate}")
        Text(text = "Duración: ${trip.tripDuration()} días")

        // Aquí puedes añadir otros detalles del viaje
        // como la información de actividades y lugares

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { /* Editar button click handler */ }) {
                Text(text = "Editar")
            }

            Button(onClick = { /* Eliminar button click handler */ }) {
                Text(text = "Eliminar")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TripDetailsScreenPreview() {
    ParcialAPPTheme {
        TripDetailsScreen(trip = Trip(id = 1, startDate = LocalDate.now(), endDate = LocalDate.now().plusDays(5), destination = "Ciudad Ejemplo"))
    }
}