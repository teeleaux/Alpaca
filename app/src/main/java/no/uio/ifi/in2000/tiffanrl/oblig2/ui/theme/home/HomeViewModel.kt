package no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas.PartiesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.Districts
import no.uio.ifi.in2000.tiffanrl.oblig2.network.AlpacaAPI
import no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.AlpacaPartyUIState


class HomeViewModel : ViewModel() {

    private val votesRepository: VotesRepository = VotesRepository()
    private val partiesRepository: PartiesRepository = AlpacaPartiesRepository()
    private val _errorState = MutableStateFlow<String?>(null)

    var partiesUIState by mutableStateOf(AlpacaPartyUIState(parties = listOf()))
        private set

    var votesD1UIState by mutableStateOf(Districts(districts = listOf()))
        private set
    var votesD2UIState by mutableStateOf((Districts(districts = listOf())))
    var votesD3UIState by mutableStateOf((Districts(districts = listOf())))

    var errorState: StateFlow<String?> = _errorState


    init{
        loadParties()
        getDistrict1Votes()
        getDistrict2Votes()
        getDistrict3Votes()
    }

    private fun loadParties() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
            val parties = partiesRepository.getAlpacaPartiesData()
            partiesUIState = partiesUIState.copy(parties = parties)
        } catch (e: IOException) {
            _errorState.value = "No Internet Connection"
        } catch (e: Exception) {
            _errorState.value = "Bleep bloop error" //handle other errors
        }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            val listResult = AlpacaAPI.retrofitService.getData()
            partiesUIState = listResult
        }
    }


    private fun getDistrict1Votes() {
        viewModelScope.launch(Dispatchers.IO){
            val districtVotes = votesRepository.getDistrict1Votes()
            votesD1UIState = votesD1UIState.copy(districtVotes.districts)
        }
    }

    private fun getDistrict2Votes() {
        viewModelScope.launch(Dispatchers.IO) {
            val district2Votes = votesRepository.getDistrict2Votes()
            votesD2UIState = votesD2UIState.copy(district2Votes.districts)
        }
    }

    private fun getDistrict3Votes() {
        viewModelScope.launch(Dispatchers.IO) {
            val district3Votes = votesRepository.getAggVotes()
            votesD3UIState = votesD3UIState.copy(district3Votes)
        }
    }

}



