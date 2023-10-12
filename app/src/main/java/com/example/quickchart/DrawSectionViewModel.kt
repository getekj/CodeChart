package com.example.quickchart.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.Ink
import com.google.mlkit.vision.digitalink.RecognitionResult


class DrawSectionViewModel : ViewModel() {

//    // Specify the recognition model for a language
//    var modelIdentifier: DigitalInkRecognitionModelIdentifier
//
//    fun download_model() {
//        try {
//            modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US")!!
//        } catch (e: MlKitException) {
//            // language tag failed to parse, handle error.
//        }
//        if (modelIdentifier == null) {
//            // no model was found, handle error.
//        }
//    }

    var recognitionModel: DigitalInkRecognitionModel =
        DigitalInkRecognitionModel.builder(DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US")!!).build()


    // Get a recognizer for the language
    var recognizer: DigitalInkRecognizer =
        DigitalInkRecognition.getClient(
            DigitalInkRecognizerOptions.builder(recognitionModel).build())

    fun recognize_ink(ink: Ink) {

        recognizer.recognize(ink)
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