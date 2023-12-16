package com.example.quickchart.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quickchart.R
import com.example.quickchart.data.InterventionDataSource
import com.example.quickchart.model.Intervention


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressColumn (
    modifier: Modifier = Modifier,
    interventions: List<Intervention>,
    //addIntervention: (String, String) -> Unit,
    //interventionViewModel: InterventionsViewModel,
    //interventionState: InterventionDataSource,
) {

    Column( modifier = Modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Interventions",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        val intList = remember { mutableStateOf(emptyList<Intervention>()) }

        val newIntervention = interventions.toList()
        intList.value = newIntervention

        println("In progress column, interventions is : $interventions and intList.value is ${intList.value}")


        InterventionColumn(interventionsList = intList.value)

        PulseCheck()

    }


}

@Composable
fun InterventionCard(intervention: Intervention) {

    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.medium_grey), shape = RoundedCornerShape(10.dp))
            .border(
                width = 4.dp,
                shape = RoundedCornerShape(10.dp),
                color = colorResource(id = R.color.dark_grey)
            ),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = intervention.Time,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = intervention.Description,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                Icons.Rounded.Edit,
                contentDescription = "Edit Intervention Button",
                modifier = Modifier
                    .size(40.dp)
                    .border(width = 2.dp, color = Color.Black)
                    .padding(4.dp)
                    .clickable(enabled = true, onClickLabel = "Edit", role = Role.Companion.Button,
                        onClick = {/*TODO*/ })
            )

            Spacer(modifier = Modifier.size(12.dp))

            Icon(
                Icons.Rounded.Delete,
                contentDescription = "Delete Intervention Button",
                modifier = Modifier
                    .size(40.dp)
                    .border(width = 2.dp, color = Color.Black)
                    .padding(4.dp)
            )
        }

    }

}

@Composable
fun InterventionColumn(
    interventionsList: List<Intervention>,
    modifier: Modifier = Modifier
) {

    println("Inside intervention column interventions is: $interventionsList")

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .height(800.dp)
    ) {
        items(interventionsList) { intervention ->
            InterventionCard(intervention = intervention)
        }
    }
}

@Composable
fun PulseCheck(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Last Pulse Check:",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = "0:47",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.medium_grey), shape = RoundedCornerShape(10.dp))
                .height(60.dp)
                .width(150.dp)
        )
    }
}
