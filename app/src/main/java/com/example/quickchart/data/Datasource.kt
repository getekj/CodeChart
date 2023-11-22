package com.example.quickchart.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.quickchart.model.Intervention
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

data class InterventionDataSource (

//    val intList = MutableStateFlow(mutableListOf<Intervention>())
////    val intList = mutableStateListOf<Intervention>()
//    val intListFlow: Flow<MutableList<Intervention>> = intList.asStateFlow()
//    fun updateIntList(newIntList: MutableList<Intervention>) {
//        intList.value = newIntList
//        println("inside datasource, int list is updated to: " + intList.value)
//    }


    var interventionsList: MutableList<Intervention> = mutableListOf<Intervention>()



//    fun loadInterventions(): MutableList<Intervention> {
//        println("load interventions fxn: " + interventionsList)
//        return interventionsList
//    }

//    fun addIntervention(time: String, description: String) {
//        val intervention = Intervention(time, description)
//
////        interventionsList.add(intervention)
////        println("intervention " + description + " added!")
////        println("intervention list is now: " + interventionsList)
//
//        // testing the intList
//        updateIntList(interventionsList)
//
//    }


)

