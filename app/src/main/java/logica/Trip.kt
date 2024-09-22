import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

import java.time.temporal.ChronoUnit

class Trip(
    val id: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val destination: String
) {

    companion object {
        fun createTrip(id: Int, startDate: LocalDate, endDate: LocalDate, destination: String): Trip {
            return Trip(id, startDate, endDate, destination)
        }
    }
    // Calcula la duración del viaje en días
    @RequiresApi(Build.VERSION_CODES.O)
    fun tripDuration(): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }

    // Verifica si una fecha está dentro del rango del viaje
    @RequiresApi(Build.VERSION_CODES.O)
    fun isDateInRange(date: LocalDate): Boolean {
        return !date.isBefore(startDate) && !date.isAfter(endDate)
    }

    // Devuelve una descripción del viaje
    @RequiresApi(Build.VERSION_CODES.O)
    fun tripDetails(): String {
        return "Viaje a $destination del $startDate al $endDate (Duración: ${tripDuration()} días)"
    }
}


