import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Trip(
    val id: Int = (1..100).random(), // Genera un ID aleatorio si no se pasa uno
    val startDate: LocalDate,
    val endDate: LocalDate,
    val destination: String
) : Serializable {

    companion object {
        fun createTrip(id: Int, startDate: LocalDate, endDate: LocalDate, destination: String): Trip {
            return Trip(id, startDate, endDate, destination)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun tripDuration(): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editTrip(newStartDate: LocalDate, newEndDate: LocalDate, newDestination: String): Trip {
        return Trip(
            id = this.id, // Mantiene el mismo ID
            startDate = newStartDate,
            endDate = newEndDate,
            destination = newDestination
        )
    }

}
