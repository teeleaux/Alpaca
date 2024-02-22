package no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.party

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas.PartiesRepository
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo

class PartyViewModel: ViewModel() {
    private val partiesRepository: PartiesRepository = AlpacaPartiesRepository()

    private var initializeCalled = false

    private val _partiesUIState = mutableStateOf<PartyInfo?>(null)
    val partyInfo: State<PartyInfo?> = _partiesUIState

    fun initialize(partyId: String) {
        if (initializeCalled) return
        initializeCalled = true
        getPartyById(partyId)
    }

    // Use getSinglePartyData from repository
    private fun getPartyById(partyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentParty = partiesRepository.getSinglePartyData(partyId)
            Log.i("PartyViewModel", "getPartyById PVM $partyId")
            _partiesUIState.value = currentParty//null safety
        }
    }
}
        //return _partiesUIState.value.parties.find { it.id == partyId } ?: PartyInfo.default() //null safety



//private val _partiesUIState = MutableStateFlow(AlpacaPartyUIState(parties = listOf()))
//val partiesUIState: StateFlow<AlpacaPartyUIState> = _partiesUIState.asStateFlow()
//private var partiesUIState by mutableStateOf(AlpacaPartyUIState(parties = listOf()))


// should edit this function to only get selected PartyInfo object and not entire list
/*private fun loadParties() {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            val parties = partiesRepository.getAlpacaPartiesData()
            _partiesUIState.value = _partiesUIState.value.copy(parties = parties)
            Log.i("parties PVM", parties.size.toString())
        } catch (e:Exception) {
            Log.e("PartyViewModel", "Error loading parties: ${e.message}")
        }
    }
}*/