package google_home_automations

val invocationsPrefixes = mapOf(
    Pair(Action.Raise, setOf("alza", "tira su")),
    Pair(Action.Lower, setOf("abbassa", "tira giù")),
    Pair(Action.Stop, setOf("ferma", "stoppa")),
)

operator fun Map<Action, Set<String>>.plus(some: Set<String>): Map<Action, Set<String>> {
    return mapValues {
        it.value.flatMap { prefix -> some.map { "$prefix $it" } }.toSet()
    }
}
