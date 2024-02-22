package no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.party

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyScreen(partyId: String, partyViewModel: PartyViewModel = viewModel(), navController: NavController) {

    // initializes partyViewModels partyId, turns it from null to passing a partyId
    partyViewModel.initialize(partyId)

    val party = partyViewModel.partyInfo.value
    Log.i("PartyScreen", "Party name: ${party?.name}")

    party?.let { alpacaParty ->

        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text("Party Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.padding(25.dp))
                Text(
                    alpacaParty.name,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                        .padding(vertical = 4.dp)
                        //.clip(CircleShape)
                        .background(color = Color(android.graphics.Color.parseColor(alpacaParty.color)))
                ) {
                    val imageModifier = Modifier.fillMaxSize().clip(CircleShape)
                    AsyncImage(
                        model = alpacaParty.img,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = imageModifier
                    )
                }
                Text(
                    text = ("Leader: " + alpacaParty.leader),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = alpacaParty.description,
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
    }
}



