package com.example.quickchart.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quickchart.R
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

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.pink))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Patient MRN: $patient",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 4.dp, end = 8.dp, bottom = 4.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.dark_grey))
            ) {

                Text(
                    text = "End",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }

        DrawSection(drawViewModel = drawViewModel, addIntervention = addIntervention, interventions = interventions)


    }
}



@Preview
@Composable
fun CodeChartScreenPreview() {

}