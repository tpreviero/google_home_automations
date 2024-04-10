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
    Pair(Action.Raise, listOf("alza", "tira su")),
    Pair(Action.Lower, listOf("abbassa", "tira giù")),
    Pair(Action.Stop, listOf("ferma", "stoppa")),
)

val invocations = invocationsPrefixes.map { entry ->
    Pair(entry.key, entry.value.flatMap { prefix -> singleInvocations.map { "$prefix $it" } }.toSet())
}.toMap()

val invocationsMultiple = invocationsPrefixes.map { entry ->
    Pair(entry.key, entry.value.flatMap { prefix -> multipleInvocations.map { "$prefix $it" } }.toSet())
}.toMap()

val invocationsAll = invocationsPrefixes.map { entry ->
    Pair(entry.key, entry.value.flatMap { prefix -> allInvocations.map { "$prefix $it" } }.toSet())
}.toMap()
