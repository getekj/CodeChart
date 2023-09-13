package com.example.quickchart.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun CodeChartScreen(patient: String, navController: NavController ) {
    Text(text = "this is code chart screen patient id: $patient")

}



@Preview
@Composable
fun CodeChartScreenPreview() {

}