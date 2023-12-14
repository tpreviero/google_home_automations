package google_home_automations

data class Automation(
    val name: String,
    val description: String,
    val starters: Set<Starters>,
    val actions: List<Actions>
)