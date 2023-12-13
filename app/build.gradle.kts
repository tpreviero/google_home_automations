plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"

    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("google_home_automations.GoogleHomeAutomationsKt")
}
