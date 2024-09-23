import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ud.parcialapp.MainActivity
import com.ud.parcialapp.Trip
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddTrip() {
        // Crea un nuevo viaje
        val newTrip = Trip(
            id = 1,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(5),
            destination = "Cancún"
        )

        // Agregar el viaje a la lista
        composeTestRule.activity.run {
            trips.add(newTrip) // Asegúrate de que 'trips' es accesible aquí
        }

        // Verifica que el viaje se haya agregado
        val trips = composeTestRule.activity.getTrips()
        assertEquals(1, trips.size)
        assertEquals("Cancún", trips[0].destination)
    }

    @Test
    fun testDeleteTrip() {
        // Agregar un viaje a la lista
        val tripToDelete = Trip(
            id = 2,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(5),
            destination = "Madrid"
        )

        composeTestRule.activity.run {
            trips.add(tripToDelete)
        }

        // Eliminar el viaje
        composeTestRule.activity.run {
            deleteTripById(tripToDelete.id)
        }

        // Verifica que la lista esté vacía
        val trips = composeTestRule.activity.getTrips()
        assertTrue(trips.isEmpty())
    }

    @Test
    fun testSearchFunctionality() {
        // Agregar algunos viajes
        val trip1 = Trip(
            id = 3,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(5),
            destination = "París"
        )
        val trip2 = Trip(
            id = 4,
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusDays(3),
            destination = "Londres"
        )

        composeTestRule.activity.run {
            trips.add(trip1)
            trips.add(trip2)
        }

        // Simular búsqueda
        composeTestRule.activity.searchQuery = "París"
        val filteredTrips = composeTestRule.activity.getTrips().filter { trip ->
            trip.destination.contains(composeTestRule.activity.searchQuery, ignoreCase = true)
        }

        // Verifica que se haya filtrado correctamente
        assertEquals(1, filteredTrips.size)
        assertEquals("París", filteredTrips[0].destination)
    }
}