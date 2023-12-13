package google_home_automations

data class RollerShutter(val room: String, val device: String)

val rollerShutters = listOf(
    RollerShutter("cucina", "tapparella grande"),
    RollerShutter("ufficio", "tapparella"),
    RollerShutter("cameretta", "tapparella"),
    RollerShutter("camera padronale", "tapparella grande"),
    RollerShutter("camera padronale", "tapparella piccola"),
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
