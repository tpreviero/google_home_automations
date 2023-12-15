package google_home_automations

data class Automation(
    val name: String,
    val description: String,
    val starters: Set<Starters>,
    val actions: List<Actions>
)

fun RollerShutter.automation(action: Action) = Automation(
    "$action $device $room roller shutter",
    "Automation to ${action.toString().lowercase()} $device roller shutter for room $room",
    starters(action),
    actions(action)
)

fun List<RollerShutter>.automation(action: Action, additionalStarters: () -> Set<Starters> = { emptySet() }): Automation {
    return Automation(
        "$action ${rooms()} roller shutters",
        "Automation to ${action.toString().lowercase()} roller shutters for rooms ${rooms()}",
        starters(action) + additionalStarters(),
        actions(action)
    )
}

private fun List<RollerShutter>.rooms(): String = map { it.room }.toSet().joinToString(", ")