package com.example.quickchart.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProgressColumn (
    modifier: Modifier = Modifier.padding(8.dp)
) {
    
    LazyColumn() {
        item {
            Text(text = "hi" )
        }
    }
    
    
}