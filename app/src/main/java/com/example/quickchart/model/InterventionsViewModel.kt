package com.example.quickchart.model

import androidx.lifecycle.ViewModel
import com.example.quickchart.data.InterventionDataSource

class InterventionsViewModel : ViewModel() {

    val interventionDataSource = InterventionDataSource()

    var interventions: MutableList<Intervention> = interventionDataSource.interventionsList

    fun add_intervention(time: String, description: String) {

        interventionDataSource.addIntervention(time, description)

    }

    fun load_intervention() {

        this.interventions = interventionDataSource.loadInterventions()

    }
}