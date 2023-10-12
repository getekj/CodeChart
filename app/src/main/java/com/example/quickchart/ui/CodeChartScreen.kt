package com.example.quickchart.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.example.quickchart.R
//import com.example.quickchart.StrokeManager.recognize

@Composable
fun CodeChartScreen(patient: String, navController: NavController, viewModel: DrawSectionViewModel ) {

    Box(
        modifier = Modifier.background(Color.White)
    ) {
        Text(text = "this is code chart screen patient id: $patient")
        DrawSection(viewModel = viewModel)
    }
}



@Preview
@Composable
fun CodeChartScreenPreview() {

}