package no.uio.ifi.in2000.tiffanrl.oblig2.network

import no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.AlpacaPartyUIState
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json"

// retrofit is unnecessary here use a try/catch in repository class

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AlpacaAPIService {
    @GET("data")
    suspend fun getData(): AlpacaPartyUIState
}

object AlpacaAPI{
    val retrofitService: AlpacaAPIService by lazy {
        retrofit.create(AlpacaAPIService::class.java)
    }
}






