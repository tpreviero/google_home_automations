package google_home_automations

private val singleInvocations = setOf(
    "la %s della ",
    "la %s in ",
    "la %s dell'",
    "la %s del ",
    "la %s in ",
    "%s della ",
    "%s in ",
    "%s dell'",
    "%s del ",
    "%s in ",
    "%s ",
)

private val multipleInvocations = setOf(
    "le tapparelle della ",
    "le tapparelle in ",
    "le tapparelle dell'",
    "le tapparelle del ",
    "le tapparelle dei ",
    "le tapparelle in ",
    "le tapparelle nei ",
    "le tapparelle nella ",
    "le tapparelle nelle ",
    "le tapparelle negli ",
    "tapparelle della ",
    "tapparelle in ",
    "tapparelle dell'",
    "tapparelle del ",
    "tapparelle dei ",
    "tapparelle in ",
    "tapparelle nei ",
    "tapparelle nella ",
    "tapparelle nelle ",
    "tapparelle negli ",
    "tapparelle ",
)

private val allInvocations = setOf(
    "tapparelle",
    "tapparelle casa",
    "tapparelle della casa",
    "le tapparelle",
    "le tapparelle casa",
    "le tapparelle della casa",
    "tutte le tapparelle",
    "tutte le tapparelle casa",
    "tutte le tapparelle della casa",
)

private val invocationsPrefixes = mapOf(
    Pair(Action.Raise, setOf("alza", "tira su")),
    Pair(Action.Lower, setOf("abbassa", "tira giù")),
    Pair(Action.Stop, setOf("ferma", "stoppa")),
)

val invocations = invocationsPrefixes + singleInvocations

val invocationsMultiple = invocationsPrefixes + multipleInvocations

val invocationsAll = invocationsPrefixes + allInvocations

operator fun Map<Action, Set<String>>.plus(some: Set<String>): Map<Action, Set<String>> {
    return mapValues {
        it.value.flatMap { prefix -> some.map { "$prefix $it" } }.toSet()
    }
}
