package google_home_automations

import java.io.OutputStreamWriter

fun OutputStreamWriter.printStarters(
    it: List<RollerShutter>, starters: (List<RollerShutter>) -> List<String>
) {
    append("  starters:")
    starters(it).forEach(::printStarter)
}

fun OutputStreamWriter.printStarter(invocation: String) {
    append(
        """
    - type: assistant.event.OkGoogle
      eventData: query
      is: "$invocation""""
    )
}

fun OutputStreamWriter.printScheduledStarter() {
    append(
        """
    - type: time.schedule
      at: 22:00
      weekdays:
        - MON
        - TUE
        - WED
        - THU
        - FRI
        - SAT
        - SUN
    - type: time.schedule
      at: 10:00
      weekdays:
        - MON
        - TUE
        - WED
        - THU
        - FRI
        - SAT
        - SUN"""
    )
}

fun OutputStreamWriter.printActions(rollerShutter: List<RollerShutter>, action: Action) {
    append("\n  actions:")
    rollerShutter.forEach {
        printAction(it, action)
    }
}

fun OutputStreamWriter.printAction(rollerShutter: RollerShutter, action: Action) {
    append(
        """
    - type: device.command.OnOff
      on: false
      devices: ${rollerShutter.device} ${
            if (action == Action.Raise) Action.Lower.toString().lowercase() else Action.Raise.toString().lowercase()
        } - ${rollerShutter.room}
    - type: device.command.OnOff
      on: ${if (action == Action.Stop) "false" else "true"}
      devices: ${rollerShutter.device} ${
            if (action == Action.Raise) Action.Raise.toString().lowercase() else Action.Lower.toString().lowercase()
        } - ${rollerShutter.room}"""
    )
}

fun OutputStreamWriter.printPreamble(name: String, description: String) {
    appendLine(
        """
metadata:
  name: $name
  description: $description
automations:
""".trimIndent()
    )
}