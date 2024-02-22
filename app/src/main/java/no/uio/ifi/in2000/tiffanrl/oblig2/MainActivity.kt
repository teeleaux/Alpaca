package no.uio.ifi.in2000.tiffanrl.oblig2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.MyApplicationTheme
import no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.home.AlpacaPartyHomeScreen
import no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.party.PartyScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // run starts here
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "homescreen") {
                        composable("partyscreen/{partyId}") { backStackEntry ->
                            val partyId = backStackEntry.arguments?.getString("partyId") ?: ""
                            PartyScreen(partyId = partyId, navController = navController)
                            Log.i("Main: partyId", partyId)
                        }
                        composable("homescreen") {
                            AlpacaPartyHomeScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}
