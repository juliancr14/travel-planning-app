import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Trip(
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
    fun isDateInRange(date: LocalDate): Boolean {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun tripDetails(): String {
        return "Viaje a $destination del $startDate al $endDate (Duración: ${tripDuration()} días)"
    }
}
