package com.ud.parcialapp

import Trip
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.parcialapp.ui.theme.ParcialAPPTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(modifier: Modifier = Modifier) {
    var destination by remember { mutableStateOf("") }
    val trips = remember {
        mutableStateListOf(
            Trip(1, LocalDate.parse("2024-09-20"), LocalDate.parse("2024-09-30"), "Cali"),
            Trip(2, LocalDate.parse("2024-10-20"), LocalDate.parse("2024-10-30"), "Medellín"),
            Trip(3, LocalDate.parse("2024-11-20"), LocalDate.parse("2024-11-30"), "Bucaramanga")
        )
    }

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
                    Text(text = "Duración: ${trip.tripDuration()} días", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Usar LocalContext aquí
        val context = LocalContext.current
        Button(onClick = {
            val intent = Intent(context, TripFormActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Agregar")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TripsScreenPreview() {
    ParcialAPPTheme {
        TripsScreen()
    }
}
