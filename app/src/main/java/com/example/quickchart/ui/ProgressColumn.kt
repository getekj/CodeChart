package com.example.quickchart.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickchart.data.InterventionDataSource
import com.example.quickchart.model.Intervention


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressColumn (
    modifier: Modifier = Modifier.padding(8.dp),
    interventions: List<Intervention>,
    addIntervention: (String, String) -> Unit,
    //interventionViewModel: InterventionsViewModel,
    interventionState: InterventionDataSource,
) {


    val intList = remember { mutableStateOf(emptyList<Intervention>()) }


    println("In progress column, interventions is : $interventions and intList.value is ${intList.value}" )


    InterventionColumn(interventionsList = intList.value)


    Column() {
        Button(onClick = {
            println("button clicked, interventions is: " + interventions)
            println("and int list is " + intList.value)
            val newIntervention = interventions.toList()
            intList.value = newIntervention

        }) {
            Text("Update Interventions")
        }
        Button(onClick = {
            addIntervention("2304", "TESTTTT")
        }) {
            Text("ADD TEST")
        }
    }


}

@Composable
fun InterventionCard(intervention: Intervention) {
    Text(modifier = Modifier.padding(8.dp),
        text = intervention.Time + intervention.Description)
}

@Composable
fun InterventionColumn(
    interventionsList: List<Intervention>,
    modifier: Modifier = Modifier
) {

    println("Inside intervention column interventions is: $interventionsList")

    LazyColumn( modifier = Modifier.padding(4.dp)) {
        items(interventionsList) { intervention ->
            InterventionCard(intervention = intervention)
        }
    }
}
