package com.example.quickchart.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.quickchart.data.InterventionDataSource
import com.example.quickchart.model.Intervention


@Composable
fun CodeChartScreen(
    patient: String,
    navController: NavController,
    drawViewModel: DrawSectionViewModel,
    //interventionViewModel: InterventionsViewModel,
    addIntervention: (String, String) -> Unit,
    interventions: List<Intervention>,
    interventionState: InterventionDataSource
) {

    //val interventionState by remember { mutableStateOf(interventions) }
    println("In code chart screen, interventions is $interventions")

    Box(
        modifier = Modifier.background(Color.White)
    ) {
        Text(text = "this is code chart screen patient id: $patient")
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
                ) {
            DrawSection(drawViewModel = drawViewModel, addIntervention = addIntervention, interventions = interventions)
            //ProgressColumn(interventions = interventions, addIntervention = addIntervention, interventionState = interventionState)
        }

    }
}



@Preview
@Composable
fun CodeChartScreenPreview() {

}