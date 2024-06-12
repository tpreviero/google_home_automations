package google_home_automations

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val client: HttpClient = HttpClient.newBuilder().build()

fun insertAutomation(automation: Automation): String {
    val insertBody =
        "[\"$HOUSE_ID\",[\"\",null,null,null,\"\",null,null,[],2,null,null,null,null,null,[\"${automation.http()}\"]],[[\"script_details.content\"]]]"
    val requestBuilder = getRequestBuilder("UpsertAutomation")
    val insertRequest = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(insertBody)).build()
    val insertResponse: HttpResponse<String> = client.send(insertRequest, HttpResponse.BodyHandlers.ofString())
    println("Add automation: ${insertResponse.statusCode()}")
    return "\"([^\"]*)\"".toRegex().find(insertResponse.body())?.groups?.get(1)?.value!!
}


private fun getRequestBuilder(functionality: String): HttpRequest.Builder {
    return HttpRequest.newBuilder()
        .uri(URI.create("https://googlehomefoyer-pa.clients6.google.com/\$rpc/google.internal.home.foyer.v1.AutomationService/$functionality"))
        .header("content-type", "application/json+protobuf")
        .header("authorization", "Bearer $GOOGLE_AUTH_TOKEN")
}

fun enableAutomation(automationId: String): Result<Boolean> {
    println(automationId)
    val enableBody =
        "[\"$HOUSE_ID\",[\"$automationId\",null,null,null,null,null,null,[[1]],2],[[\"status.is_enabled\"]]]"
    val enableRequest =
        getRequestBuilder("UpsertAutomation").POST(HttpRequest.BodyPublishers.ofString(enableBody)).build()
    val enableResponse = client.send(enableRequest, HttpResponse.BodyHandlers.ofString())
    println("Enable automation: ${enableResponse.statusCode()}")
    if (enableResponse.statusCode() != 200) {
        return Result.failure(Exception("Failed to enable automation: error code ${enableResponse.statusCode()}"))
    }
    return Result.success(true)
}

fun deleteAutomation(automationId: String) {
    val deleteBody = "[\"$HOUSE_ID\",\"$automationId\"]"
    val deleteRequest =
        getRequestBuilder("DeleteAutomation").POST(HttpRequest.BodyPublishers.ofString(deleteBody)).build()
    val enableResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString())
    println("Delete automation $automationId: ${enableResponse.statusCode()}")
}

private fun Starters.http(): String = when (this) {
    is Starters.OkGoogle -> """
    - type: assistant.event.OkGoogle
      eventData: query
      is: \"$invocation\""""

    is Starters.Scheduled -> """
    - type: time.schedule
      at: ${hour.ordinal}:${minute.ordinal}
      weekdays:""" + weekdays.map { it.toString().substring(0..2).uppercase() }.joinToString("") {
        """
        - $it"""
    }
}

private fun Actions.http(): String = when (this) {
    is Actions.Delay -> """
    - type: time.delay
      for: ${duration.inWholeSeconds}sec"""

    is Actions.OnOff -> """
    - type: device.command.OnOff
      on: $on
      devices:
        """ + devices.joinToString("\n        - ", "- ")
}

private fun Automation.http(): String {
    return """metadata:
  name: $name
  description: $description
automations:
  starters:""" + starters.joinToString("") { it.http() } + """
  actions:""" + actions.joinToString("") { it.http() }
}
