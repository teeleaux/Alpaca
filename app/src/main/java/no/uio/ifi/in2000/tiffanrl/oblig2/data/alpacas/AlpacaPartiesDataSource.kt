package no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas

import com.google.gson.Gson
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo
import java.net.URL

fun fetchAlpacaPartiesData(jsonUrl: String): List<PartyInfo> {
    val jsonString = URL(jsonUrl).readText()
    val gson = Gson()
    return gson.fromJson(jsonString, Array<PartyInfo>::class.java).toList()
}