package google_home_automations

import kotlin.time.Duration

sealed interface Actions {
    data class OnOff(val on: Boolean, val devices: Set<String>)
    data class Delay(val duration: Duration)
}