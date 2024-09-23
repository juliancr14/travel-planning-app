package com.ud.parcialapp

import Trip
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.ud.parcialapp.ui.theme.ParcialAPPTheme

class MainActivity : ComponentActivity() {
    private lateinit var tripLauncher: ActivityResultLauncher<Intent>
    private val trips = mutableStateListOf<Trip>() // Lista de viajes
    private var searchQuery by mutableStateOf("") // Consulta de búsqueda

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Registramos el launcher para recibir el resultado
        tripLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val trip: Trip? = result.data?.getSerializableExtra("trip") as? Trip
                val deletedTripId: Int? = result.data?.getIntExtra("deleted_trip_id", -1)
                trip?.let {
                    trips.add(it) // Agregar el nuevo viaje a la lista
                }

                // Eliminar viaje de la lista si se recibió un ID
                deletedTripId?.let {
                    if (it != -1) {
                        deleteTripById(it)
                    }
                }
            }
        }

        setContent {
            ParcialAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripsScreen(
                        modifier = Modifier.padding(innerPadding),
                        tripLauncher = tripLauncher,
                        trips = trips,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { query -> searchQuery = query }
                    )
                }
            }
        }
    }

    private fun deleteTripById(tripId: Int) {
        // Filtra la lista para eliminar el viaje con el ID correspondiente
        trips.removeIf { it.id == tripId }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    modifier: Modifier = Modifier,
    tripLauncher: ActivityResultLauncher<Intent>,
    trips: List<Trip>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val filteredTrips = trips.filter { trip ->
        trip.destination.contains(searchQuery, ignoreCase = true)
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
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Destino") }
            )
            Button(onClick = { /* Lógica de búsqueda adicional si es necesaria */ }) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar viajes filtrados
        filteredTrips.forEach { trip ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Encabezado con el destino
                    Text(
                        text = "Destino: ${trip.destination.uppercase()}",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Detalles del viaje en una fila (Row)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = "Fecha inicio:", style = MaterialTheme.typography.bodyMedium)
                            Text(text = trip.startDate.toString(), style = MaterialTheme.typography.bodyMedium)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Fecha fin:", style = MaterialTheme.typography.bodyMedium)
                            Text(text = trip.endDate.toString(), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    val context = LocalContext.current
                    Button(onClick = {
                        val intent = Intent(context, TripDetailsActivity::class.java)
                        intent.putExtra("trip", trip)
                        tripLauncher.launch(intent)
                    }) {
                        Text("Ver Detalles")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Duración: ${trip.tripDuration()} días", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current
        Button(onClick = {
            val intent = Intent(context, TripFormActivity::class.java)
            tripLauncher.launch(intent)
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
        TripsScreen(
            tripLauncher = object : ActivityResultLauncher<Intent>() {
                override fun launch(input: Intent) {}
                override val contract: ActivityResultContract<Intent, *>
                    get() = TODO()

                override fun launch(input: Intent, options: ActivityOptionsCompat?) {
                    TODO()
                }

                override fun unregister() {}
            },
            trips = emptyList(),
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}
