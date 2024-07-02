package google_home_automations

import Percentage
import google_home_automations.Action.Raise
import google_home_automations.Starters.Scheduled.*
import of

val toNeutral = automation {
    starters(Starters.Scheduled(Hours._22, Minutes._30, Weekdays.entries.toSet()))
    actions(Action.Stop(rollerShutters))
}

val colazioneEstiva = automation {
    invocations("colazione estiva")
    actions(
        Raise(tapparellaGrandeCucina.of(Percentage._40)) +
        Raise(
            tapparellaPiccolaCucina,
            tapparellaUfficio,
            tapparellaBagnoNuovo,
            tapparellaBagnoVecchio,
        )
    )
}

val customizedAutomations: List<Pair<String, Automation>> = listOf(
    "😐✋ stop all @22.30" to toNeutral,
    "☕️☀️ colazioneEstiva" to colazioneEstiva,
)

fun automation(init: Automation.() -> Automation): Automation =
    init(Automation("", "", emptySet(), emptyList()))

fun Automation.invocations(vararg invocations: String) =
    Automation(this.name, this.description, invocations.map { Starters.OkGoogle(it) }.toSet(), this.actions)

fun Automation.starters(vararg starters: Starters) =
    Automation(this.name, this.description, starters.toSet(), this.actions)

operator fun Action.invoke(vararg rollerShutters: RollerShutter) =
    this(rollerShutters.toList())

operator fun Action.invoke(rollerShutters: List<RollerShutter>) =
    rollerShutters.actions(this)

fun Automation.actions(vararg actions: Actions) =
    Automation(this.name, this.description, this.starters, actions.toList())

fun Automation.actions(actions: List<Actions>) = Automation(this.name, this.description, this.starters, actions)
