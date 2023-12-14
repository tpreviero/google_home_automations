package google_home_automations

fun Starters.yaml(): String = when (this) {
    is Starters.OkGoogle -> """
    - type: assistant.event.OkGoogle
      eventData: query
      is: "$invocation""""

    is Starters.Scheduled -> """
    - type: time.schedule
      at: ${hour.ordinal}:${minute.ordinal}
      weekdays:""" + weekdays.map { toString().substring(0..2).uppercase() }.map {
        """
        - $it"""
    }
}

fun Actions.yaml(): String = when (this) {
    is Actions.Delay -> """
    - type: time.delay
      for: ${duration.inWholeSeconds}sec"""

    is Actions.OnOff -> """
    - type: device.command.OnOff
      on: $on
      devices:
        """ + devices.joinToString("\n        - ", "- ")
}

fun Automation.yaml(): String {
    return """metadata:
  name: $name
  description: $description
automations:
  starters:""" + starters.joinToString("") { it.yaml() } + """
  actions:""" + actions.joinToString("") { it.yaml() }
}
