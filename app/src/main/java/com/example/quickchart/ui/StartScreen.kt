package com.example.quickchart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun QuickChartApp() {

    val navController = rememberNavController()
    val viewModel: DrawSectionViewModel = viewModel()
//    viewModel.download_model()

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
            CodeChartScreen(patient = patient_id, navController = navController, viewModel = viewModel)
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

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "QuickChart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "Enter patient's MRN or scan wristband:",
            fontSize = 16.sp,
            modifier = modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        PatientBar(navController)

        Spacer(modifier = Modifier.size(24.dp))

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
            placeholder = { Text("Enter Patient MRN")},
            modifier = Modifier,

        )
        Button(
            onClick = { navController.navigate("chartscreen/${patientID.value.text}") },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text ="Enter",
                fontSize = 16.sp
            )
        }

    }

}

@Composable
fun SkipButton(modifier: Modifier = Modifier) {

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "Skip",
            fontSize = 16.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
fun QuickChartPreview() {
    QuickChartTheme {
        QuickChartApp()
    }
}