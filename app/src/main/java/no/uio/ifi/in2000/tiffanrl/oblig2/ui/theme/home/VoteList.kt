package no.uio.ifi.in2000.tiffanrl.oblig2.ui.theme.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import no.uio.ifi.in2000.tiffanrl.oblig2.model.votes.DistrictVotes


// Define the VoteList composable
@Composable
fun VoteList(votes: List<DistrictVotes>) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    { Text("Party ID", style = typography.titleLarge)
        Text("Votes", style = typography.titleLarge) }
    LazyColumn {
        items(votes) { vote ->
            // Display each vote item
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom)
            {
                Text(vote.alpacaPartyId , style = typography.bodyLarge, color = Color.Black)
                Text(vote.numberOfVotesForParty.toString(), style = typography.bodyLarge)
            }
        }
    }
}