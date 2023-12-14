package google_home_automations

import google_home_automations.Starters.OkGoogle

sealed interface Starters {
    data class OkGoogle(val invocation: String) : Starters
}

fun RollerShutter.starters(action: Action): List<OkGoogle> =
    invocations[action]!!.map { i -> (i + this.room).format(this.device) }.map(::OkGoogle)

fun List<RollerShutter>.starters(action: Action): List<OkGoogle> {
    return if (map { it.room }.all { it == first().room }) {
        invocationsMultiple[action]!!.map { i -> i + this.first().room }.map(::OkGoogle)
    } else {
        invocationsAll[action]!!.map(::OkGoogle)
    }
}