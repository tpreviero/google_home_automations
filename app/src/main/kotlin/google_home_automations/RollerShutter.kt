package google_home_automations

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

 data class Room(
    val name: String,
    val article: String = "della",
)

val cucina = Room("cucina")
val ufficio = Room("ufficio", "dell'")
val cameretta = Room("cameretta")
val camera = Room("camera")
val bagnoVecchio = Room("bagno vecchio", "del")
val bagnoNuovo = Room("bagno nuovo", "del")

data class RollerShutter(
    val room: Room,
    val device: String,
    val groups: Set<String> = emptySet(),
    val transitionDuration: Duration = 60.seconds,
)

val rollerShutters = listOf(
    RollerShutter(cucina, "tapparella grande", setOf("mattina", "est"), 37.seconds),
    RollerShutter(cucina, "tapparella piccola", setOf("mattina", "est"), 25.seconds),
    RollerShutter(ufficio, "tapparella", setOf("mattina", "ovest"), 37.seconds),
    RollerShutter(cameretta, "tapparella", setOf("sera", "est"), 20.seconds),
    RollerShutter(camera, "tapparella grande", setOf("sera", "est"), 20.seconds),
    RollerShutter(camera, "tapparella piccola", setOf("sera", "est"), 13.seconds),
    RollerShutter(bagnoVecchio, "tapparella", setOf("mattina", "sera", "ovest"), 15.seconds),
    RollerShutter(bagnoNuovo, "tapparella", setOf("mattina", "ovest"), 25.seconds),
)

enum class Action {
    Raise, Lower, Stop;

    fun emojify() =
        when (this) {
            Raise -> "🔼"
            Lower -> "🔽"
            Stop -> "✋"
        }
}
