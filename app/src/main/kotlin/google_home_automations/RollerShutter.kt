package google_home_automations

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

 data class Room(
    val name: String,
    val article: String = "della",
 ) {
     @Override
     override fun toString() = name
 }

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

val tapparellaGrandeCucina = RollerShutter(cucina, "tapparella grande", setOf("mattina", "est"), 37.seconds)
val tapparellaPiccolaCucina = RollerShutter(cucina, "tapparella piccola", setOf("mattina", "est"), 25.seconds)
val tapparellaUfficio = RollerShutter(ufficio, "tapparella", setOf("mattina", "ovest"), 37.seconds)
val tapparellaCameretta = RollerShutter(cameretta, "tapparella", setOf("sera", "est"), 20.seconds)
val tapparellaGrandeCamera = RollerShutter(camera, "tapparella grande", setOf("sera", "est"), 20.seconds)
val tapparellaPiccolaCamera = RollerShutter(camera, "tapparella piccola", setOf("sera", "est"), 13.seconds)
val tapparellaBagnoVecchio = RollerShutter(bagnoVecchio, "tapparella", setOf("mattina", "sera", "ovest"), 15.seconds)
val tapparellaBagnoNuovo = RollerShutter(bagnoNuovo, "tapparella", setOf("mattina", "ovest"), 25.seconds)

val rollerShutters = listOf(
    tapparellaGrandeCucina,
    tapparellaPiccolaCucina,
    tapparellaUfficio,
    tapparellaCameretta,
    tapparellaGrandeCamera,
    tapparellaPiccolaCamera,
    tapparellaBagnoVecchio,
    tapparellaBagnoNuovo,
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
