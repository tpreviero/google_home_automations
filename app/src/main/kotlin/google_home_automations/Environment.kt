package google_home_automations

val DRY_RUN = System.getenv("DRY_RUN").toBoolean()
val HOUSE_ID: String get() = System.getenv("HOUSE_ID")
val GOOGLE_AUTH_TOKEN: String get() = System.getenv("GOOGLE_AUTH_TOKEN")