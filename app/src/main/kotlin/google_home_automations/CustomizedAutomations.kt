package google_home_automations

import Percentage
import google_home_automations.Action.Lower
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

val abbassaUnPo = automation {
    name = "Abbassa un po'"
    description = "Abbassa le tapparelle un po'"
    starters += invocations("abbassa un po' le tapparelle")
    actions += Lower(rollerShutters.map { it.of(Percentage._20) })
}

val alzaUnPo = automation {
    name = "Alza un po'"
    description = "Alza le tapparelle un po'"
    starters += invocations("alza un po' le tapparelle")
    actions += Raise(rollerShutters.map { it.of(Percentage._20) })
}

val abbassaUnPoEach: List<Pair<String, Automation>> = rollerShutters.map {
    "🔽🤏 abbassa un po' ${it.device} ${it.room.name}" to automation {
        name = "Abbassa un po' ${it.device} ${it.room.name}"
        description = "Abbassa un po' ${it.device} ${it.room.name}"
        starters += invocations("abbassa un po' la ${it.device} ${it.room.article} ${it.room.name}")
        actions += Lower(it.of(Percentage._20))
    }
}

val alzaUnPoEach: List<Pair<String, Automation>> = rollerShutters.map {
    "🔼🤏 alza un po' ${it.device} ${it.room.name}" to automation {
        name = "Alza un po' ${it.device} ${it.room.name}"
        description = "Alza un po' ${it.device} ${it.room.name}"
        starters += invocations("alza un po' la ${it.device} ${it.room.article} ${it.room.name}")
        actions += Lower(it.of(Percentage._20))
    }
}

val customizedAutomations: List<Pair<String, Automation>> = listOf(
    "😐✋ stop all @22.30" to toNeutral,
    "☕️☀️ colazione estiva" to colazioneEstiva,
    "🔽🤏 abbassa un po'" to abbassaUnPo,
    "🔼🤏 alza un po'" to alzaUnPo,
) + abbassaUnPoEach + alzaUnPoEach

fun automation(init: Automation.() -> Unit): Automation = Automation("", "", emptySet(), emptyList()).apply { init() }

fun invocations(vararg invocations: String) = invocations.map { Starters.OkGoogle(it) }.toSet()

operator fun Action.invoke(vararg rollerShutters: RollerShutter) =
    this(rollerShutters.toList())

operator fun Action.invoke(rollerShutters: List<RollerShutter>) =
    rollerShutters.actions(this)
