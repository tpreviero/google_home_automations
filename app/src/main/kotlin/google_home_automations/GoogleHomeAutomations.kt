package google_home_automations

import google_home_automations.Action.*
import google_home_automations.Starters.Scheduled.Hours._22
import google_home_automations.Starters.Scheduled.Minutes._00
import google_home_automations.Starters.Scheduled.Weekdays
import java.io.File

val DRY_RUN = System.getenv("DRY_RUN").toBoolean()
val HOUSE_ID: String get() = System.getenv("HOUSE_ID")
val GOOGLE_AUTH_TOKEN: String get() = System.getenv("GOOGLE_AUTH_TOKEN")

fun main(args: Array<String>) {

    with(File("${args[0]}/automations-ids")) {
        if (exists() && !DRY_RUN) {
            forEachLine { deleteAutomation(it) }
            delete()
        }
    }

    val automations =
        automationPerDevice(rollerShutters, Raise) +
                automationPerDevice(rollerShutters, Lower) +
                automationPerDevice(rollerShutters, Stop) +
                automationPerRoom(rollerShutters, Raise) +
                automationPerRoom(rollerShutters, Lower) +
                automationPerRoom(rollerShutters, Stop) +
                listOf(
                    automationPerHouse(rollerShutters, Raise),
                    automationPerHouse(rollerShutters, Lower),
                    automationPerHouse(rollerShutters, Stop),
                )

    automations.forEach {
        File("${args[0]}/${it.first}.yml").writeBytes(it.second.yaml().toByteArray())
        if (!DRY_RUN) {
            save(it.second, "${args[0]}/automations-ids")
        }
    }
}

private fun automationPerHouse(
    rollerShutters: List<RollerShutter>, action: Action
): Pair<String, Automation> {
    return "${action.emojify()} all" to Automation(
        "$action ${rollerShutters.rooms()} roller shutters",
        "Automation to ${
            action.toString().lowercase()
        } roller shutters for rooms ${rollerShutters.rooms()}",
        rollerShutters.starters(action).toSet().apply {
            if (action == Stop) this + Starters.Scheduled(_22, _00, Weekdays.entries.toSet())
        },
        rollerShutters.actions(action)
    )
}


private fun automationPerRoom(
    rollerShutters: List<RollerShutter>, action: Action
): List<Pair<String, Automation>> {
    return rollerShutters.groupBy(RollerShutter::room).map { (room, rs) ->
        "${action.emojify()} $room" to Automation(
            "$action $room roller shutters",
            "Automation to ${action.toString().lowercase()} roller shutter for room $room",
            rs.starters(action).toSet(),
            rs.actions(action)
        )
    }
}

private fun automationPerDevice(
    rollerShutters: List<RollerShutter>, action: Action
): List<Pair<String, Automation>> {
    return rollerShutters.map {
        "${action.emojify()} ${it.device} ${it.room}" to Automation(
            "$action ${it.device} ${it.room} roller shutters",
            "Automation to ${action.toString().lowercase()} ${it.device} roller shutter for room ${it.room}",
            it.starters(action).toSet(),
            it.actions(action)
        )
    }
}

fun save(automation: Automation, idFile: String) {
    val automationId = insertAutomation(automation)
    enableAutomation(automationId)
    File(idFile).appendText("$automationId\n")
}

private fun List<RollerShutter>.rooms(): String = joinToString(", ") { it.room }
