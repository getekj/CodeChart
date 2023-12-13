package com.example.quickchart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickchart.ui.theme.QuickChartTheme
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quickchart.R
import com.example.quickchart.model.InterventionsViewModel

@Composable
fun QuickChartApp(
    interventionViewModel: InterventionsViewModel = viewModel()
) {

    val navController = rememberNavController()
    val drawViewModel: DrawSectionViewModel = viewModel()

    drawViewModel.digitalInkModel.download_model()

    val uiState by interventionViewModel.uiState.collectAsState()


    NavHost(navController = navController, startDestination = "startscreen") {
        composable("startscreen") {
            StartScreen(navController = navController)
        }
        composable("chartscreen/{patient_id}",
            arguments = listOf(navArgument("patient_id") {
                type = NavType.StringType
            })
            ) {backStackEntry ->
            val patient_id = remember {
                backStackEntry.arguments?.getString("patient_id") ?: ""
            }
//            CodeChartScreen(patient = patient_id, navController = navController,
//                drawViewModel = drawViewModel, interventionViewModel = interventionViewModel)
            CodeChartScreen(
                patient = patient_id,
                navController = navController,
                drawViewModel = drawViewModel,
                //interventionViewModel = interventionViewModel,
                addIntervention = { time: String, description: String ->
                    interventionViewModel.add_intervention(time, description)
                },
                interventions = uiState.interventionsList,
                interventionState = uiState
            )
        }
    }

}

@Composable
fun StartScreen(navController: NavController, modifier: Modifier = Modifier) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)) {

        Spacer(modifier = Modifier.size(100.dp))

        TitleBanner(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "Enter Patient's MRN:",
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        PatientBar(navController)

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            text = "Or Scan Wristband:",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )

        ScanButton()

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            text = "Add Information Later:",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )

        SkipButton()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientBar(navController: NavController, modifier: Modifier = Modifier) {

    val patientID = remember {
        mutableStateOf(TextFieldValue())
    }

    Row(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center ) {

        TextField(
            value = patientID.value,
            onValueChange = { patientID.value = it},
            singleLine = true,
            placeholder = {
                Text(
                    text = "ie. TP789209",
                    fontSize = 28.sp
                )},
            modifier = Modifier.padding(8.dp).defaultMinSize(minHeight = 80.dp, minWidth = 400.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.labelMedium
        )

        Button(
            onClick = {
                navController.navigate("chartscreen/${patientID.value.text}") },
            modifier = Modifier.padding(start = 36.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
        ) {
            Text(
                text ="Next",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

    }

}

@Composable
fun SkipButton(modifier: Modifier = Modifier) {

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(start = 36.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
    ) {
        Text(
            text = "Skip",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }

}

@Composable
fun ScanButton(modifier: Modifier = Modifier) {

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(start = 36.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
    ) {
        Text(
            text = "Scan",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }

}

@Composable
fun TitleBanner(modifier: Modifier) {


    Image(
        painter = painterResource(id = R.drawable.codechartlogolarge),
        contentDescription = "Quick Chart Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .width(900.dp)
            .background(colorResource(id = R.color.pink))
    )

}


@Preview(showBackground = true)
@Composable
fun QuickChartPreview() {
    QuickChartTheme {
        QuickChartApp()
    }
}