package google_home_automations

import Percentage
import google_home_automations.Action.Raise
import google_home_automations.Starters.Scheduled.*
import of

val toNeutral = automation {
    name = "Stop all @22.30"
    description = "Posizione neutra switches at 22.30"
    starters += Starters.Scheduled(Hours._22, Minutes._30, Weekdays.entries.toSet())
    actions += Action.Stop(rollerShutters)
}

val colazioneEstiva = automation {
    name = "Colazione estiva"
    description = "Alza le tapparelle per una colazione estiva"
    starters += invocations("colazione estiva")
    actions += Raise(tapparellaGrandeCucina.of(Percentage._40))
    actions += Raise(
        tapparellaPiccolaCucina,
        tapparellaUfficio,
        tapparellaBagnoNuovo,
        tapparellaBagnoVecchio,
    )
}

val customizedAutomations: List<Pair<String, Automation>> = listOf(
    "😐✋ stop all @22.30" to toNeutral,
    "☕️☀️ colazioneEstiva" to colazioneEstiva,
)

fun automation(init: Automation.() -> Unit): Automation = Automation("", "", emptySet(), emptyList()).apply { init() }

fun invocations(vararg invocations: String) = invocations.map { Starters.OkGoogle(it) }.toSet()

operator fun Action.invoke(vararg rollerShutters: RollerShutter) =
    this(rollerShutters.toList())

operator fun Action.invoke(rollerShutters: List<RollerShutter>) =
    rollerShutters.actions(this)
