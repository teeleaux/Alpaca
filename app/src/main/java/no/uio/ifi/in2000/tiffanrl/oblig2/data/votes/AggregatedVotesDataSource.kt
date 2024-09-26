package no.uio.ifi.in2000.tiffanrl.oblig2.data.votes

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.District
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.DistrictVotes

data class PartyVotesList(
    val parties: List<PartyVotes>
)

data class PartyVotes(
    val partyId: String,
    val votes: Int
    )

class AggregatedVotesDataSource(
    private val path:String = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district3.json")
{
    private val client = HttpClient { //oppretter HTTP client
        install(ContentNegotiation) { // verktøyet for å lese inn JSON format
            gson() //caller gson verktøy for å hente JSON response
        }
    }

    suspend fun fetchAggregatedVotes(): List<DistrictVotes>{
        val district3Votes: PartyVotesList = client.get(path).body()
        Log.i("district3Votes", "$district3Votes")
        val parties: List<PartyVotes> = district3Votes.parties

        val votes = parties.map { partyVotes ->
            DistrictVotes(
                district = District.DISTRICT_3,
                alpacaPartyId = partyVotes.partyId,
                numberOfVotesForParty = partyVotes.votes
            )
        }
        return votes
    }
}

/* {
  "parties": [
    {
      "partyId": "1",
      "votes": 630192
    },
    {
      "partyId": "2",
      "votes": 430192
    },
    {
      "partyId": "3",
      "votes": 530392
    },
    {
      "partyId": "4",
      "votes": 230192
    }
  ]
}*/