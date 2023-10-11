package com.example.quickchart
//
//
//// adapted from: https://developers.google.com/ml-kit/vision/digital-ink-recognition/android
//
//import android.content.ContentValues.TAG
//import android.util.Log
//import android.view.MotionEvent
//import android.widget.TextView
//import com.google.mlkit.common.MlKitException
//import com.google.mlkit.common.model.DownloadConditions
//import com.google.mlkit.common.model.RemoteModelManager
//import com.google.mlkit.vision.digitalink.DigitalInkRecognition
//import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
//import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
//import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
//import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
//import com.google.mlkit.vision.digitalink.Ink
//import com.google.mlkit.vision.digitalink.RecognitionResult
//
//object StrokeManager {
//
//    // holds the digital ink recognition model, it is currently init to null
//    private val model: DigitalInkRecognitionModel?= null
//
//    // holds the Ink Object which is drawn from the touch screen at the addNewTouchEvent
//    var inkBuilder = Ink.builder()
//
//    // builds the stokes (pen/stylus movements) within the Ink object
//    var strokeBuilder = Ink.Stroke.builder()
//
//    // call this function each time there is a new event
//    fun addNewTouchEvent(event:MotionEvent) {
//
//        Log.i(TAG,"Entered addNewTouchEvent")
//        // returns the masked action being performed
//        val action = event.actionMasked
//        val x = event.x
//        val y = event.y
//        // timing information (may use to measure performance later but can be omitted with ink point create)
//        val t = System.currentTimeMillis()
//
//
//        when (action) {
//
//            // a pressed gesture has started, the current action is the stylus/pen is down
//            // the motion contains the initial starting location
//            MotionEvent.ACTION_DOWN -> {
//                // creating a new Ink stroke and assigning to strokeBuilder
//                strokeBuilder = Ink.Stroke.builder()
//                // adding the starting point of the stroke to the (xy coords + timestamp) to the strokeBuilder
//                strokeBuilder!!.addPoint(Ink.Point.create(x,y,t))
//
//            }
//
//            // a change happened between press gesture (btwn ACTION_DOWN and ACTION_UP)
//            // the motion contains the most recent point and any intermediate pts since the last down
//            // or move event
//            MotionEvent.ACTION_MOVE -> strokeBuilder!!.addPoint(Ink.Point.create(x,y,t))
//
//            // a press gesture has finished, the motion contains the final release location as
//            // well as any intermediate points since the last down or move event
//            MotionEvent.ACTION_UP -> {
//                // adds the end point to the currently active stroke
//                strokeBuilder!!.addPoint(Ink.Point.create(x,y,t))
//                // now the stroke has been fully build on release, we add it to the ink object builder to
//                // assemble all the strokes into the overall inkBuilder (the user's ink input)
//                inkBuilder.addStroke(strokeBuilder!!.build())
//
//                // clearing the stroke builder
//                strokeBuilder = Ink.Stroke.builder()
//            }
//        }
//
//        // send ink to the recognizer?
//        val ink = inkBuilder.build()
//        recognize(ink)
//
//    }
//
//    fun downloadModel() {
//
//        // Specify the recognition model for a language and download so it is available locally
//        // modelIdentifier is set to null and will be used to store the identifier for the digital ink recognition model
//        var modelIdentifier: DigitalInkRecognitionModelIdentifier ?= null
//
//        try {
//            modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US")
//        } catch (e: MlKitException) {
//            // language tag failed to parse, handle error.
//            Log.i(TAG, "Failed to parse language tag")
//        }
//        if (modelIdentifier == null) {
//            // no model was found, handle error.
//            Log.i(TAG, "No model found")
//            return
//        }
////        var model: DigitalInkRecognitionModel? =
////            modelIdentifier?.let { DigitalInkRecognitionModel.builder(it).build() }
//
//        // creates the model from identifier
//        var model = DigitalInkRecognitionModel.builder(modelIdentifier!!).build()
//
//        val remoteModelManager = RemoteModelManager.getInstance()
//
//        remoteModelManager.download(model, DownloadConditions.Builder().build())
//            .addOnSuccessListener {
//                Log.i(TAG,"Model successfully downloaded")
//            }
//            .addOnFailureListener {
//                Log.i(TAG, "Error while downloading the model")
//            }
//
//
//    }
//
//    // pass in ink object instead?
//    fun recognize(ink: Ink) {
//
//        // Get a recognizer for the language
//        var recognizer: DigitalInkRecognizer =
//            DigitalInkRecognition.getClient(
//                DigitalInkRecognizerOptions.builder(model!!).build())
//
////        // Process Ink Object
////        val ink = inkBuilder.build()
//
//        recognizer.recognize(ink)
//            .addOnSuccessListener { result: RecognitionResult ->
//                // `result` contains the recognizer's answers as a RecognitionResult.
//                // Logs the text from the top candidate.
//                Log.i(TAG, result.candidates[0].text)
//
//            }
//            .addOnFailureListener {
//
//                Log.i(TAG, "Error during recognition of ink object")
//
//            }
//
//    }
//
//    fun clear() {
//
//        inkBuilder = Ink.builder()
//
//    }
//}