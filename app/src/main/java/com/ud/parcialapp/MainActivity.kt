    package com.ud.parcialapp

    import Trip
    import android.app.Activity
    import android.content.Intent
    import android.icu.lang.UCharacter
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
    import java.time.LocalDate

    class MainActivity : ComponentActivity() {
        private lateinit var tripLauncher: ActivityResultLauncher<Intent>
        private val trips = mutableStateListOf<Trip>() // Lista de viajes

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            // Registramos el launcher para recibir el resultado
            tripLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val trip: Trip? = result.data?.getSerializableExtra("trip") as? Trip
                    trip?.let {
                        trips.add(it) // Agregar el nuevo viaje a la lista
                    }
                }
            }

            setContent {
                ParcialAPPTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        TripsScreen(modifier = Modifier.padding(innerPadding), tripLauncher = tripLauncher, trips = trips)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TripsScreen(modifier: Modifier = Modifier, tripLauncher: ActivityResultLauncher<Intent>, trips: List<Trip>) {
        var destination by remember { mutableStateOf("") }

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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // Padding alrededor de la tarjeta para mayor separación
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevación para destacar más la tarjeta
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Encabezado con el destino
                        Text(
                            text = "Destino: ${trip.destination.uppercase()}",
                            style = MaterialTheme.typography.headlineSmall, // Estilo más grande y destacado
                            modifier = Modifier.padding(bottom = 8.dp) // Espaciado debajo del encabezado
                        )

                        // Línea divisoria para separar el título de los detalles
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp), // Padding alrededor de la línea
                            thickness = 1.dp, // Grosor de la línea
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f) // Color de la línea
                        )

                        // Detalles del viaje en una fila (Row)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween // Distribuye los elementos a lo largo de la fila
                        ) {
                            // Sección para la fecha de inicio
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(text = "Fecha inicio:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = trip.startDate.toString(), style = MaterialTheme.typography.bodyMedium)
                            }

                            // Sección para la fecha de fin
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Fecha fin:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = trip.endDate.toString(), style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        // Información adicional
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Duración: ${trip.tripDuration()} días", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(context, TripFormActivity::class.java)
                tripLauncher.launch(intent) // Usar el launcher para iniciar la actividad
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
            TripsScreen(tripLauncher = object : ActivityResultLauncher<Intent>() {
                override fun launch(input: Intent) {}
                override val contract: ActivityResultContract<Intent, *>
                    get() = TODO("Not yet implemented")

                override fun launch(input: Intent, options: ActivityOptionsCompat?) {
                    TODO("Not yet implemented")
                }

                override fun unregister() {}
            }, trips = emptyList())
        }
    }
