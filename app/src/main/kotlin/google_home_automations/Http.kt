package google_home_automations

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val client: HttpClient = HttpClient.newBuilder().build()

fun insertAutomation(automation: String): String {
    val insertBody =
        "[\"$HOUSE_ID\",[\"\",null,null,null,\"\",null,null,[],2,null,null,null,null,null,[\"${automation}\"]],[[\"script_details.content\"]]]"
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

fun enableAutomation(automationId: String) {
    println(automationId)
    val enableBody =
        "[\"$HOUSE_ID\",[\"$automationId\",null,null,null,null,null,null,[[1]],2],[[\"status.is_enabled\"]]]"
    val enableRequest =
        getRequestBuilder("UpsertAutomation").POST(HttpRequest.BodyPublishers.ofString(enableBody)).build()
    val enableResponse = client.send(enableRequest, HttpResponse.BodyHandlers.ofString())
    println("Enable automation: ${enableResponse.statusCode()}")
}

fun deleteAutomation(automationId: String) {
    println(automationId)
    val deleteBody = "[\"$HOUSE_ID\",\"$automationId\"]"
    val deleteRequest =
        getRequestBuilder("DeleteAutomation").POST(HttpRequest.BodyPublishers.ofString(deleteBody)).build()
    val enableResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString())
    println("Delete automation: ${enableResponse.statusCode()}")
}
