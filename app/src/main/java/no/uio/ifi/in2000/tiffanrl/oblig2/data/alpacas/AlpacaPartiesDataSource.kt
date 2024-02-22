package no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.Parties
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo


class AlpacaPartiesDataSource(private val path: String =
    "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json") {

    private val client = HttpClient() { //oppretter HTTP client
        install(ContentNegotiation) { // verktøyet for å lese inn JSON format
            gson() //caller gson verktøy for å hente JSON response
        }
    }

    suspend fun fetchAlpacaPartiesData(): List<PartyInfo> {
        val party: Parties = client.get(path).body()
        val response = client.get(path)
        Log.i("PartiesDataSourceFetch", "response: $response")
        return party.parties
    }

}
/*fun fetchAlpacaPartiesData(jsonUrl: String): List<PartyInfo> {
    val jsonString = URL(jsonUrl).readText()
    val gson = Gson()
    return gson.fromJson(jsonString, Array<PartyInfo>::class.java).toList()
}*/
/*        val response : Jokes = client.get(path) //fjerner body her
        Log.i("Jokesdatasource", "response: $response") // can see HTTP responses and
        // Log responses here!
*/