package no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas.PartyInfo


@Composable
fun AlpacaPartyCard(
    alpacaParty: PartyInfo,
    navController: NavController
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("partyscreen/${alpacaParty.id}") }
                .padding(0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(Color(android.graphics.Color.parseColor(alpacaParty.color)))
            ) {
            Log.i("AlpacaPartyCard", "partyscreen/${alpacaParty.id}")
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    alpacaParty.id + ": " + alpacaParty.name,
                    modifier = Modifier
                        .padding(2.dp)
                        .shadow(
                            elevation = 10.dp,
                            ambientColor = Color.White
                        ),
                    textAlign = TextAlign.Center,
                    style = typography.titleLarge,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        //.clip(CircleShape)
                        .padding(vertical = 6.dp)
                ) {
                    val imageModifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                    AsyncImage(
                        model = alpacaParty.img,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = imageModifier
                    )
                }
                Text(
                    text = "Leader:\n${alpacaParty.leader}",
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                    color = Color.Black
                )
            }
        }
    }

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AlpacaPartyHomeScreen(homeViewModel: HomeViewModel = viewModel(), navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val errorState by homeViewModel.errorState.collectAsState()

    if (errorState != null) {
        LaunchedEffect(errorState) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = errorState!!)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                ShowSnackBar(message = homeViewModel.errorState.value)
            }
        }
    ) {innerpadding ->
        Column(modifier = Modifier
            .padding(innerpadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(homeViewModel.partiesUIState.parties) { party ->
                    AlpacaPartyCard(alpacaParty = party, navController = navController)
                    Log.i("HomeScreen APC party.id", party.id)
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // Spacer between AlpacaPartyCards and AdditionalComponent

            val districtVotes = listOf(
                homeViewModel.votesD1UIState.districts,
                homeViewModel.votesD2UIState.districts,
                homeViewModel.votesD3UIState.districts
            )
            Log.i("District Votes size:", "${districtVotes.size}")

            val districtNames = listOf("District 1", "District 2", "District 3")
            var selectedDistrictIndex by remember { mutableIntStateOf(0) }
            var isDropdownExpanded by remember { mutableStateOf(false) }


            Log.i("Selected District Index:", "$selectedDistrictIndex")

            VoteList(districtVotes[selectedDistrictIndex])

            Spacer(modifier = Modifier.height(60.dp)) // Spacer between AlpacaPartyCards and AdditionalComponent

            Button(
                onClick = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                Text("Select District")
            }

            if (isDropdownExpanded) {
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    districtNames.forEachIndexed { index, districtName ->
                        DropdownMenuItem( text = { Text(districtName) },
                            onClick = { selectedDistrictIndex = index },
                            //colors = MenuItemColors
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSnackBar(message: String?) {
    Snackbar(
        modifier = Modifier.padding(16.dp),
        action = {
            // SnackbarAction(onActionClick = { /* Handle action click */ })
        }
    ) {
        Text(text = message!!, style = typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun AlpacaPartyHomeScreenPreview() {
    //AlpacaPartyHomeScreen()
}

