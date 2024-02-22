package no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas

import android.util.Log
import no.uio.ifi.in2000.tiffanrl.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.Districts

interface PartiesRepository{
    suspend fun getAlpacaPartiesData(): List<PartyInfo>
    suspend fun getSinglePartyData(partyID:String): PartyInfo
    suspend fun getD1Votes(): Districts
    suspend fun getD2Votes(): Districts
    suspend fun getVRAggVotes(): List<DistrictVotes>
}

class AlpacaPartiesRepository (
    private val partiesDataSource: AlpacaPartiesDataSource = AlpacaPartiesDataSource(),
): PartiesRepository {
    // takes in DataSource as parameter and returns a repository

    // has a variable votesRepository which can get DistrictVotes
    private val votesRepository: VotesRepository = VotesRepository()

    // make a mutable variable of _votesRepository in order to show votes without altering VotesRepository?

    override suspend fun getAlpacaPartiesData(): List<PartyInfo> {

        return partiesDataSource.fetchAlpacaPartiesData()
    }
    override suspend fun getSinglePartyData(partyID: String): PartyInfo {
        val parties = partiesDataSource.fetchAlpacaPartiesData()
        Log.i("Repo getSinglePartyData call", parties.size.toString())
        return parties.find { it.id == partyID }
            ?: throw NoSuchElementException("Party with ID $partyID not found")
    }
    override suspend fun getD1Votes(): Districts {
        return votesRepository.getDistrict1Votes()
    }

    override suspend fun getD2Votes(): Districts {
        return votesRepository.getDistrict2Votes()
    }

    override suspend fun getVRAggVotes(): List<DistrictVotes> {
        return votesRepository.getAggVotes()
    }



    /*override suspend fun getSinglePartyData(partyID: String): PartyInfo {
        val partyId = partyID.toInt()
        val parties = partiesDataSource.fetchAlpacaPartiesData()

        if (partyId == parties.id) {
        }
        return parties.firstOrNull {it.id == partyID}

    }

    //private val alpacaParties = MutableStateFlow<List<PartyInfo>>(listOf())
    //fun fetchAlpacaParties(): StateFlow<List<PartyInfo>> = alpacaParties.asStateFlow()
*/
}