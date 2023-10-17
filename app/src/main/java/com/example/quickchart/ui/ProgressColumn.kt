package com.example.quickchart.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickchart.model.Intervention
import com.example.quickchart.model.InterventionsViewModel


@Composable
fun ProgressColumn (
    modifier: Modifier = Modifier.padding(8.dp)
) {
    val interventionsViewModel: InterventionsViewModel = viewModel()
    var interventionsList = interventionsViewModel.interventions
    
    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(interventionsList) { intervention ->
            InterventionCard(intervention = intervention)
        }
    }
    
    
}

@Composable
fun InterventionCard(intervention: Intervention) {
    Text(text = intervention.Description)
}