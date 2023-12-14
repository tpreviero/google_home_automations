package google_home_automations

import google_home_automations.Action.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStreamWriter

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
        File("${args[0]}/${it.first}.yml").writeBytes(it.second)
        if (!DRY_RUN) {
            save(it.second.toString(Charsets.UTF_8), "${args[0]}/automations-ids")
        }
    }
}

private fun automationPerHouse(
    rollerShutters: List<RollerShutter>, action: Action
): Pair<String, ByteArray> {
    val output = ByteArrayOutputStream()
    with(OutputStreamWriter(output)) {
        printPreamble(
            "$action ${rollerShutters.rooms()} roller shutters", "Automation to ${
                action.toString().lowercase()
            } roller shutters for google_home_automations.rooms ${rollerShutters.rooms()}"
        )
        printStarters(rollerShutters) { rollerShutters.starters(action).map { it.invocation } }
        apply { if (action == Stop) printScheduledStarter() }
        printActions(rollerShutters, action)
        close()
    }
    return Pair("${action.emojify()} all", output.toByteArray())
}


private fun automationPerRoom(
    rollerShutters: List<RollerShutter>, action: Action
): List<Pair<String, ByteArray>> {
    return rollerShutters.groupBy(RollerShutter::room).map { (room, rs) ->
        val output = ByteArrayOutputStream()
        with(OutputStreamWriter(output)) {
            printPreamble(
                "$action $room roller shutters",
                "Automation to ${action.toString().lowercase()} roller shutter for room $room"
            )
            printStarters(rs) { rs.starters(action).map { it.invocation } }
            printActions(rs, action)
            close()
        }
        Pair("${action.emojify()} $room", output.toByteArray())
    }
}

private fun automationPerDevice(
    rollerShutters: List<RollerShutter>, action: Action
): List<Pair<String, ByteArray>> {
    return rollerShutters.map {
        val output = ByteArrayOutputStream()
        with(OutputStreamWriter(output)) {
            printPreamble(
                "$action ${it.device} ${it.room} roller shutters",
                "Automation to ${action.toString().lowercase()} ${it.device} roller shutter for room ${it.room}"
            )
            printStarters(listOf(it)) { _ -> it.starters(action).map { it.invocation } }
            printActions(listOf(it), action)
            close()
        }
        Pair("${action.emojify()} ${it.device} ${it.room}", output.toByteArray())
    }
}

fun save(automation: String, idFile: String) {
    val automationId = insertAutomation(automation)
    enableAutomation(automationId)
    File(idFile).appendText("$automationId\n")
}

private fun List<RollerShutter>.rooms(): String = joinToString(", ") { it.room }
