package google_home_automations

import google_home_automations.Starters.OkGoogle

sealed interface Starters {
    data class OkGoogle(val invocation: String) : Starters
    data class Scheduled(val hour: Hours, val minute: Minutes, val weekdays: Set<Weekdays>) : Starters {
        enum class Hours {
            _0, _1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23
        }

        enum class Minutes {
            _00, _01, _02, _03, _04, _05, _06, _07, _08, _09, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _24, _25, _26, _27, _28, _29, _30, _31, _32, _33, _34, _35, _36, _37, _38, _39, _40, _41, _42, _43, _44, _45, _46, _47, _48, _49, _50, _51, _52, _53, _54, _55, _56, _57, _58, _59,
        }

        enum class Weekdays {
            Monday, Tuesday, Wednesday, Thursday, Friday, Sunday, Saturday,
        }
    }
}

fun RollerShutter.starters(action: Action): List<Starters> =
    invocations[action]!!.map { i -> (i + this.room).format(this.device) }.map(::OkGoogle)

fun List<RollerShutter>.starters(action: Action): List<Starters> {
    return if (map { it.room }.all { it == first().room }) {
        invocationsMultiple[action]!!.map { i -> i + this.first().room }.map(::OkGoogle)
    } else {
        invocationsAll[action]!!.map(::OkGoogle)
    }
}