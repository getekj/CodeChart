package com.example.quickchart.data

import com.example.quickchart.model.Intervention

class InterventionDataSource {

    var interventionsList: MutableList<Intervention> = mutableListOf()

    fun loadInterventions(): MutableList<Intervention> {
        return interventionsList
    }

    fun addIntervention(time: String, description: String) {
        val intervention = Intervention(time, description)
        interventionsList.add(intervention)
    }

}

interface DataSource {
    fun loadInterventions(): MutableList<Intervention>
}