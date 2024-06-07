package google_home_automations

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class RollerShutter(
    val room: String,
    val device: String,
    val groups: Set<String> = emptySet(),
    val transitionDuration: Duration = 60.seconds
)

val rollerShutters = listOf(
    RollerShutter("cucina", "tapparella grande", setOf("zona giorno", "mattina", "est")),
    RollerShutter("cucina", "tapparella piccola", setOf("zona giorno", "mattina", "est")),
    RollerShutter("ufficio", "tapparella", setOf("zona giorno", "mattina", "ovest")),
    RollerShutter("cameretta", "tapparella", setOf("zona notte", "camere", "sera", "est")),
    RollerShutter("camera", "tapparella grande", setOf("zona notte", "camere", "sera", "est")),
    RollerShutter("camera", "tapparella piccola", setOf("zona notte", "camere", "sera", "est")),
    RollerShutter("bagno vecchio", "tapparella", setOf("zona giorno", "bagni", "mattina", "sera", "ovest")),
    RollerShutter("bagno nuovo", "tapparella", setOf("zona giorno", "bagni", "mattina", "ovest")),
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
