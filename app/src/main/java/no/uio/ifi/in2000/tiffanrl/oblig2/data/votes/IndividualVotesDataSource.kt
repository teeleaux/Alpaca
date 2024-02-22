package no.uio.ifi.in2000.tiffanrl.oblig2.data.votes

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.District
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.Districts
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.IndividualDistrictVotes

class IndividualVotesDataSource(
    private val pathDistrict1:String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district1.json",
    private val pathDistrict2:String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district2.json"
) {

    private val client = HttpClient { //makes HTTP client
        install(ContentNegotiation) { // tool for å lese inn JSON format
            gson() //caller gson tool for getting a JSON response
        }
    }

    // alter to pass a string as parameter
    suspend fun fetchDistrict1Votes(): Districts {
        val votes: List<IndividualDistrictVotes> = client.get(pathDistrict1).body()
        //maybe make a Districts class which holds val districts: List<DistrictVotes>

        // Group by the second variable and count occurrences — maps incorrectly
        val tallyMap = votes.groupBy { it.id }.mapValues { it.value.size }

        Log.i("votes IVDS fetchD1V call", "${votes.size}")

        val districtVotesList = tallyMap.map { (alpacaPartyId, numberOfVotes) ->
            val district = District.DISTRICT_1
            DistrictVotes(district, alpacaPartyId, numberOfVotes)
        }

        return Districts(districtVotesList)
    }

    suspend fun fetchDistrict2Votes(): Districts {
        val votes: List<IndividualDistrictVotes> = client.get(pathDistrict2).body()

        val tallyMap = votes.groupBy { it.id }.mapValues { it.value.size }

        val districtVotesList = tallyMap.map { (alpacaPartyId, numberOfVotes) ->
            val district = District.DISTRICT_2
            DistrictVotes(district, alpacaPartyId, numberOfVotes)
        }

        return Districts(districtVotesList)
    }
}