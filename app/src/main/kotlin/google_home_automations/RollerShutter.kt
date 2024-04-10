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
    RollerShutter("cucina", "tapparella grande", setOf("zona giorno")),
    RollerShutter("cucina", "tapparella piccola", setOf("zona giorno")),
    RollerShutter("ufficio", "tapparella", setOf("zona giorno")),
    RollerShutter("cameretta", "tapparella", setOf("zona notte", "camere")),
    RollerShutter("camera", "tapparella grande", setOf("zona notte", "camere")),
    RollerShutter("camera", "tapparella piccola", setOf("zona notte", "camere")),
    RollerShutter("bagno vecchio", "tapparella", setOf("zona giorno", "bagni")),
    RollerShutter("bagno nuovo", "tapparella", setOf("zona giorno", "bagni")),
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
