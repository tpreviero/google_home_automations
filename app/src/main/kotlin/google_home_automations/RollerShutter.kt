package google_home_automations

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class RollerShutter(val room: String, val device: String, val transitionDuration: Duration = 60.seconds)

val rollerShutters = listOf(
    RollerShutter("cucina", "tapparella grande"),
    RollerShutter("cucina", "tapparella piccola"),
    RollerShutter("ufficio", "tapparella"),
    RollerShutter("cameretta", "tapparella"),
    RollerShutter("camera", "tapparella grande"),
    RollerShutter("camera", "tapparella piccola"),
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
