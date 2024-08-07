package google_home_automations

import java.io.File

val automations: List<Pair<String, Automation>> =
    single +
    roomsAutomations +
    groups +
    all +
    customizedAutomations

fun main(args: Array<String>) {

    val folder = args[0]
    val automationsId = File("$folder/automations-ids")

    deleteExistingAutomations(automationsId)

    writeToFile(automations, folder)

    createAutomations(automationsId, automations)
}

private fun createAutomations(automationsId: File, automations: List<Pair<String, Automation>>) {
    if (!DRY_RUN) {
        automations
            .map { insertAutomation(it.second) }
            .map { Pair(it, enableAutomation(it)) }
            .map {
                automationsId.appendText("${it.first}\n")
                it
            }
            .forEach { (id, status) -> println("Automation $id enabled: $status") }
    }
}

private fun writeToFile(automations: List<Pair<String, Automation>>, folder: String) {
    automations.forEach {
        File("$folder/${it.first}.yml").writeBytes(it.second.yaml().toByteArray())
    }
}

private fun deleteExistingAutomations(automationIdsFile: File) {
    with(automationIdsFile) {
        if (exists() && !DRY_RUN) {
            readLines().parallelStream().forEach { deleteAutomation(it) }
            delete()
        }
    }
}
