package com.example.quickchart

import android.content.ContentValues.TAG
import android.util.Log
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


class ModelManager {

    private var model: DigitalInkRecognitionModel? = null

    // Specify the recognition model for a language
    var modelIdentifier: DigitalInkRecognitionModelIdentifier? = null

    var recognizer: DigitalInkRecognizer? = null

    val remoteModelManager = RemoteModelManager.getInstance()


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
            }
            .addOnFailureListener { e: Exception ->
                Log.e(TAG, "Error during recognition: $e")
            }

    }
}