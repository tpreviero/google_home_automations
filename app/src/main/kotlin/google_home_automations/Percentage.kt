
import google_home_automations.RollerShutter
import kotlin.time.Duration

enum class Percentage {
    _10, _20, _30, _40, _50, _60, _70, _80, _90, _100;

    fun of(transitionDuration: Duration): Duration {
        val percentage = this.name.substring(1).toInt()
        return transitionDuration * percentage / 100
    }

    @Override
    override fun toString() = "${name.substring(1)}%"
}

fun RollerShutter.of(percentage: Percentage) = RollerShutter(
    this.room,
    this.device,
    this.groups,
    percentage.of(this.transitionDuration),
)