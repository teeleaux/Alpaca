package no.uio.ifi.in2000.tiffanrl.oblig2.data.votes

import android.util.Log
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.Districts

interface DistrictVotesRepository{
    suspend fun getDistrict1Votes(): Districts
    suspend fun getDistrict2Votes(): Districts
    suspend fun getAggVotes(): List<DistrictVotes>
    //suspend fun getSingleDistrictVotes(districtId: String): DistrictVotes
}

// two types of votesDataSource: both individual and aggregated

class VotesRepository(
    private val votesDataSource: IndividualVotesDataSource = IndividualVotesDataSource()): DistrictVotesRepository
{
    private val aggregatedVotesDataSource: AggregatedVotesDataSource = AggregatedVotesDataSource()

    override suspend fun getDistrict1Votes(): Districts {
        val list = votesDataSource.fetchDistrict1Votes()
        Log.i("getDistrict1Votes call", "size of list: ${list.districts.size}")

        return votesDataSource.fetchDistrict1Votes()
    }

    override suspend fun getDistrict2Votes(): Districts {
        val list = votesDataSource.fetchDistrict2Votes()
        Log.i("getDistrict2Votes call", "size of list: ${list.districts.size}")
        return votesDataSource.fetchDistrict2Votes()
    }

    override suspend fun getAggVotes(): List<DistrictVotes> {
        return aggregatedVotesDataSource.fetchAggregatedVotes()
    }

    /*override suspend fun getSingleDistrictVotes(districtId: String): DistrictVotes {
        return listOf<DistrictVotes>(votesDataSource.fetchDistrict1Votes())
    }*/

}