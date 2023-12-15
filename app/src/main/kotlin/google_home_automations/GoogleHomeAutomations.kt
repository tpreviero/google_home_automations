package google_home_automations

import google_home_automations.Action.Stop
import google_home_automations.Starters.Scheduled.Hours._22
import google_home_automations.Starters.Scheduled.Minutes._30
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
        Action.entries.flatMap { action -> rollerShutters.map { "${action.emojify()} ${it.device} ${it.room}" to it.automation(action) } } +
        Action.entries.flatMap { action -> rollerShutters.groupBy(RollerShutter::room).map { (room, rs) -> "${action.emojify()} $room" to rs.automation(action) } } +
        Action.entries.map { action -> "${action.emojify()} all" to rollerShutters.automation(action) {
            listOf(Starters.Scheduled(_22, _30, Weekdays.entries.toSet())).filter { action == Stop }.toSet()
        }}

    automations.forEach {
        File("${args[0]}/${it.first}.yml").writeBytes(it.second.yaml().toByteArray())
        if (!DRY_RUN) {
            save(it.second, "${args[0]}/automations-ids")
        }
    }
}

fun save(automation: Automation, idFile: String) {
    val automationId = insertAutomation(automation)
    enableAutomation(automationId)
    File(idFile).appendText("$automationId\n")
}
