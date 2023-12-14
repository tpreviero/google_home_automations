package google_home_automations

import kotlin.time.Duration

sealed interface Actions {
    data class OnOff(val on: Boolean, val devices: Set<String>) : Actions
    data class Delay(val duration: Duration) : Actions
}

fun RollerShutter.actions(action: Action): List<Actions> {
    return when (action) {
        Action.Raise -> listOf(
            Actions.OnOff(
                false, setOf(
                    "$device ${Action.Lower.toString().lowercase()} - $room"
                )
            ),
            Actions.OnOff(
                true, setOf(
                    "$device ${Action.Raise.toString().lowercase()} - $room"
                )
            ),
            Actions.Delay(transitionDuration),
            Actions.OnOff(
                false, setOf(
                    "$device ${Action.Raise.toString().lowercase()} - $room"
                )
            ),
        )

        Action.Lower -> listOf(
            Actions.OnOff(
                false, setOf(
                    "$device ${Action.Raise.toString().lowercase()} - $room"
                )
            ),
            Actions.OnOff(
                true, setOf(
                    "$device ${Action.Lower.toString().lowercase()} - $room"
                )
            ),
            Actions.Delay(transitionDuration),
            Actions.OnOff(
                false, setOf(
                    "$device ${Action.Lower.toString().lowercase()} - $room"
                )
            ),
        )

        Action.Stop -> listOf(
            Actions.OnOff(
                false, setOf(
                    "$device ${Action.Raise.toString().lowercase()} - $room",
                    "$device ${Action.Lower.toString().lowercase()} - $room",
                )
            )
        )
    }
}

fun List<RollerShutter>.actions(action: Action): List<Actions> {
    return when (action) {
        Action.Raise -> listOf(
            Actions.OnOff(false, map { "${it.device} ${Action.Lower.toString().lowercase()} - ${it.room}" }.toSet()),
            Actions.OnOff(true, map { "${it.device} ${Action.Raise.toString().lowercase()} - ${it.room}" }.toSet()),
            Actions.Delay(maxOf(RollerShutter::transitionDuration)),
            Actions.OnOff(false, map { "${it.device} ${Action.Raise.toString().lowercase()} - ${it.room}" }.toSet()),
        )

        Action.Lower -> listOf(
            Actions.OnOff(false, map { "${it.device} ${Action.Raise.toString().lowercase()} - ${it.room}" }.toSet()),
            Actions.OnOff(true, map { "${it.device} ${Action.Lower.toString().lowercase()} - ${it.room}" }.toSet()),
            Actions.Delay(maxOf(RollerShutter::transitionDuration)),
            Actions.OnOff(false, map { "${it.device} ${Action.Lower.toString().lowercase()} - ${it.room}" }.toSet()),
        )

        Action.Stop -> listOf(
            Actions.OnOff(
                false,
                map { "${it.device} ${Action.Raise.toString().lowercase()} - ${it.room}" }.toSet()
                        + map { "${it.device} ${Action.Lower.toString().lowercase()} - ${it.room}" }.toSet()
            )
        )
    }
}