package com.example.quickchart.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchart.data.InterventionDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InterventionsViewModel : ViewModel() {

    //val interventionDataSource = InterventionDataSource()

    val _uiState = MutableStateFlow(InterventionDataSource())
    val uiState: StateFlow<InterventionDataSource> = _uiState.asStateFlow()

    var interventions: MutableList<Intervention> = mutableListOf()

    /*TESTING*/
    private val intList = MutableStateFlow(emptyList<Intervention>())
    val intListStateFlow = intList.asStateFlow()
    /*END TESTING*/

    fun add_intervention(time: String, description: String) {
        val new_intervention: Intervention = Intervention(time, description)
        interventions.add(new_intervention)

        _uiState.update { currentState ->
            currentState.copy(
                interventionsList = interventions
            )
        }

        /*TESTING*/
        intList.value = interventions
    }

//    fun load_intervention(): MutableList<Intervention> {
//        var interventions = _uiState.value.interventionsList
//        return interventions
//    }




//    var interventions: MutableList<Intervention> = interventionDataSource.interventionsList
//
//
//    fun add_intervention(time: String, description: String) {
//
//        interventionDataSource.addIntervention(time, description)
//
//    }

//    fun load_interventions() {
//
//        this.interventions = interventionDataSource.loadInterventions()
//
//    }
}