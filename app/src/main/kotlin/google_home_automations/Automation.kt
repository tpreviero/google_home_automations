package google_home_automations

data class Automation(
    val name: String,
    val description: String,
    val starters: Set<Starters>,
    val actions: List<Actions>
)

val single = Action.entries.flatMap { action -> rollerShutters.map {"${action.emojify()} ${it.device} ${it.room}" to it.automation(action) } }
val roomsAutomations = Action.entries.flatMap { action -> rollerShutters.groupBy(RollerShutter::room).map { (room, rs) -> "${action.emojify()} $room" to rs.automation(action) } }
val stopAll = Action.entries.map { action -> "${action.emojify()} all" to rollerShutters.automation(action) {
    listOf(Starters.Scheduled(
        Starters.Scheduled.Hours._22,
        Starters.Scheduled.Minutes._30, Starters.Scheduled.Weekdays.entries.toSet())).filter { action == Action.Stop }.toSet()
}}
val groups = Action.entries.flatMap { action -> rollerShutters.groups().map { "${action.emojify()} ${it.key}" to it.value.automation(action, it.key) } }

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

fun List<RollerShutter>.automation(action: Action, group: String, additionalStarters: () -> Set<Starters> = { emptySet() }): Automation {
    return Automation(
        "$action $group roller shutters",
        "Automation to ${action.toString().lowercase()} roller shutters for group $group",
        starters(action, group) + additionalStarters(),
        actions(action)
    )
}

private fun List<RollerShutter>.groups(): Map<String, List<RollerShutter>> {
    return flatMap { rs -> rs.groups.map { Pair(it, rs) } }
        .groupBy { it.first }
        .mapValues { it.value.map { it.second } }
}

private fun List<RollerShutter>.rooms(): String = map { it.room }.toSet().joinToString(", ")