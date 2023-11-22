package com.example.quickchart.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchart.ModelManager
import com.example.quickchart.data.InterventionDataSource
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.Ink
import com.google.mlkit.vision.digitalink.RecognitionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.sql.DataSource


class DrawSectionViewModel : ViewModel() {

    var reset = mutableStateOf(false)

    val digitalInkModel = ModelManager()

//    var recognitionText: String = ""


    fun download_di_model() {

        digitalInkModel.download_model()

    }

    fun recognize_ink(ink: Ink) {

        digitalInkModel.recognize_ink(ink)

    }

    fun clear_ink() {
        reset.value = true
    }

    fun reset_ink() {
        reset.value = false
    }



}