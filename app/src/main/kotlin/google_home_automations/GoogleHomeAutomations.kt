package google_home_automations

import java.io.File

val DRY_RUN = System.getenv("DRY_RUN").toBoolean()
val HOUSE_ID: String get() = System.getenv("HOUSE_ID")
val GOOGLE_AUTH_TOKEN: String get() = System.getenv("GOOGLE_AUTH_TOKEN")

val automations: List<Pair<String, Automation>> =
    single +
    roomsAutomations +
    groups +
    stopAll

fun main(args: Array<String>) {

    with(File("${args[0]}/automations-ids")) {
        if (exists() && !DRY_RUN) {
            readLines().parallelStream().forEach { deleteAutomation(it) }
            delete()
        }
    }

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
