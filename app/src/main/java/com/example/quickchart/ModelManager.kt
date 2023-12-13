package com.example.quickchart

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.quickchart.model.Intervention
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
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ModelManager {

    private var model: DigitalInkRecognitionModel? = null

    // Specify the recognition model for a language
    var modelIdentifier: DigitalInkRecognitionModelIdentifier? = null

    var recognizer: DigitalInkRecognizer? = null

    val remoteModelManager = RemoteModelManager.getInstance()

    private val recognitionText = MutableStateFlow("")
    val recognitionTextFlow = recognitionText.asStateFlow()

    fun updateRecognitionText(newRecognitionText: String) {
        recognitionText.value = newRecognitionText
    }
//
//    val time_format = "yyyy-MM-dd  HH:mm:ss"
//    @RequiresApi(Build.VERSION_CODES.O)
//    val formatter = DateTimeFormatter.ofPattern(time_format)


    fun download_model() {

        try {
            modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US")

        } catch (e: MlKitException) {
            // language tag failed to parse, handle error.
            Log.i(TAG, "Error - Language tag did not parse!")
        }
        if (modelIdentifier == null) {
            // no model was found, handle error.
            Log.i(TAG, "No model was found for this model identifier")
        }
        var inkModel: DigitalInkRecognitionModel =
            DigitalInkRecognitionModel.builder(modelIdentifier!!).build()

        // save model
        this.model = inkModel

        if (model == null) {
            println("model is null after save...")
        } else {
            println("model is NOT null after save.. ")
        }

        // Get a recognizer for the language
        var inkRecognizer: DigitalInkRecognizer =
            DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model!!).build()
            )

        // save recognizer
        this.recognizer = inkRecognizer


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun recognize_ink(ink: Ink) {

        // need to download new data prior to any recognition
        remoteModelManager.download(model!!, DownloadConditions.Builder().build())
            .addOnSuccessListener {
                Log.i(TAG, "Model downloaded")
            }
            .addOnFailureListener { e: Exception ->
                Log.e(TAG, "Error while downloading a model: $e")
            }

        recognizer!!.recognize(ink)
            .addOnSuccessListener { result: RecognitionResult ->
                // `result` contains the recognizer's answers as a RecognitionResult.
                // Logs the text from the top candidate.
                Log.i(TAG, result.candidates[0].text)
//                add_recognized_text(result.candidates[0].text)
                //recognitionText = result.candidates[0].text
                updateRecognitionText(result.candidates[0].text)
                println("Inside recognizer, recognition text is now:" + recognitionText.value)
            }
            .addOnFailureListener { e: Exception ->
                Log.e(TAG, "Error during recognition: $e")
            }
//
//        return true

    }


}