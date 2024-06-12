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
    RollerShutter("cucina", "tapparella grande", setOf("zona giorno", "mattina", "est"), 35.seconds),
    RollerShutter("cucina", "tapparella piccola", setOf("zona giorno", "mattina", "est"), 25.seconds),
    RollerShutter("ufficio", "tapparella", setOf("zona giorno", "mattina", "ovest"), 35.seconds),
    RollerShutter("cameretta", "tapparella", setOf("zona notte", "camere", "sera", "est"), 20.seconds),
    RollerShutter("camera", "tapparella grande", setOf("zona notte", "camere", "sera", "est"), 20.seconds),
    RollerShutter("camera", "tapparella piccola", setOf("zona notte", "camere", "sera", "est"), 13.seconds),
    RollerShutter("bagno vecchio", "tapparella", setOf("zona giorno", "bagni", "mattina", "sera", "ovest"), 15.seconds),
    RollerShutter("bagno nuovo", "tapparella", setOf("zona giorno", "bagni", "mattina", "ovest"), 25.seconds),
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
