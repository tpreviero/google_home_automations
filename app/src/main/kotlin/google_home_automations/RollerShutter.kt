package google_home_automations

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class RollerShutter(
    val room: String,
    val device: String,
    val groups: Set<String> = emptySet(),
    val transitionDuration: Duration = 60.seconds,
    val invocations: Set<String> = setOf("la %s della %s"),
    val invocationsMultiple: Set<String> = setOf("le tapparelle della %s"),
)

val rollerShutters = listOf(
    RollerShutter("cucina", "tapparella grande", setOf("mattina", "est"), 37.seconds),
    RollerShutter("cucina", "tapparella piccola", setOf("mattina", "est"), 25.seconds),
    RollerShutter("ufficio", "tapparella", setOf("mattina", "ovest"), 37.seconds, setOf("la %s dell'%s"), setOf("le tapparelle dell'%s")),
    RollerShutter("cameretta", "tapparella", setOf("sera", "est"), 20.seconds),
    RollerShutter("camera", "tapparella grande", setOf("sera", "est"), 20.seconds),
    RollerShutter("camera", "tapparella piccola", setOf("sera", "est"), 13.seconds),
    RollerShutter("bagno vecchio", "tapparella", setOf("mattina", "sera", "ovest"), 15.seconds, setOf("la %s del %s"), setOf("le tapparelle del %s")),
    RollerShutter("bagno nuovo", "tapparella", setOf("mattina", "ovest"), 25.seconds, setOf("la %s del %s"), setOf("le tapparelle del %s")),
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
