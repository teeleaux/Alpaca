package no.uio.ifi.in2000.tiffanrl.oblig2.data.alpacas

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo

class AlpacaPartiesRepository {

    private val alpacaParties = MutableStateFlow<List<PartyInfo>>(listOf())

    fun fetchAlpacaParties(): StateFlow<List<PartyInfo>> = alpacaParties.asStateFlow()


}