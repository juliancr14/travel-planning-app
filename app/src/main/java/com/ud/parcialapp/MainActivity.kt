package com.ud.parcialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.parcialapp.ui.theme.ParcialAPPTheme

data class Trip(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val destination: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(modifier: Modifier = Modifier) {
    var destination by remember { mutableStateOf("") }
    val trips = remember { mutableStateListOf<Trip>(
        Trip(1, "20 Septiembre", "30 Septiembre", "Cali"),
        Trip(2, "20 Octubre", "30 Octubre", "Medellin"),
        Trip(3, "20 Noviembre", "30 Noviembre", "Bucaramanga")
    ) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Mis Viajes", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destino") }
            )
            Button(onClick = { /* Buscar viajes */ }) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        trips.forEach { trip ->
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Fecha inicio: ${trip.startDate}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Fecha fin: ${trip.endDate}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Destino: ${trip.destination}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Agregar viaje */ }) {
            Text("Agregar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripsScreenPreview() {
    ParcialAPPTheme {
        TripsScreen()
    }
}