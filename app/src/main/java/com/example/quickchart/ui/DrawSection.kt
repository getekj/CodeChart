package com.example.quickchart.ui


import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.example.quickchart.model.InterventionsViewModel
import com.google.mlkit.vision.digitalink.Ink
import kotlinx.coroutines.delay


sealed class DrawEvent {
    data class Down(val x: Float, val y: Float): DrawEvent()
    data class Move(val x: Float, val y: Float): DrawEvent()
    object Up: DrawEvent()
}

private sealed class DrawPath {
    data class MoveTo(val x: Float, val y: Float): DrawPath()
    data class CurveTo(val prevX: Float, val prevY: Float, val x: Float, val y: Float): DrawPath()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawSection(
    modifier: Modifier = Modifier,
    drawViewModel: DrawSectionViewModel,
//    interventionViewModel: InterventionsViewModel
    addIntervention: (String, String) -> Unit
) {

    var time: String = "2023-06-17 09:44:09"

    val path = remember { Path() }
    var reset = drawViewModel.reset

    var drawPath by remember { mutableStateOf<DrawPath?>(null) }
    var drawEvent by remember {
        mutableStateOf<DrawEvent?>(null)
    }
    var inkBuilder = Ink.builder()
    lateinit var strokeBuilder: Ink.Stroke.Builder

    if (reset.value == true) {
        drawPath = null
        path.reset()
        drawViewModel.reset_ink()
    }

    var recognitionText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        drawViewModel.digitalInkModel.recognitionTextFlow.collect {newRecText ->
            recognitionText = newRecText
            println("rec text is:" + recognitionText)
            //interventionViewModel.add_intervention(time, recognitionText)
            addIntervention(time, recognitionText)
        }
    }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Canvas(
            modifier = modifier
                .size(600.dp, 900.dp)
                .offset(x = 10.dp, y = 50.dp)
                .background(Color.LightGray)
                .pointerInteropFilter { event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            drawPath = DrawPath.MoveTo(event.x, event.y)
                            DrawEvent.Down(event.x, event.y)

                            strokeBuilder = Ink.Stroke.builder()
                            strokeBuilder.addPoint(Ink.Point.create(event.x, event.y))
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val prevX = when (drawPath) {
                                is DrawPath.MoveTo -> (drawPath as DrawPath.MoveTo).x
                                is DrawPath.CurveTo -> (drawPath as DrawPath.CurveTo).x

                                else -> 0f
                            }

                            val prevY = when (drawPath) {
                                is DrawPath.MoveTo -> (drawPath as DrawPath.MoveTo).y
                                is DrawPath.CurveTo -> (drawPath as DrawPath.CurveTo).y

                                else -> 0f
                            }
                            drawPath = DrawPath.CurveTo(prevX, prevY, event.x, event.y)
                            DrawEvent.Move(event.x, event.y)

                            strokeBuilder!!.addPoint(Ink.Point.create(event.x, event.y))
                        }

                        MotionEvent.ACTION_UP -> {
                            DrawEvent.Up

                            strokeBuilder.addPoint(Ink.Point.create(event.x, event.y))
                            inkBuilder.addStroke(strokeBuilder.build())
                        }

                        else -> { /* do nothing */
                        }
                    }

                    return@pointerInteropFilter true
                }
        ) {
            if (drawPath == null)
                return@Canvas

            when (drawPath) {
                is DrawPath.MoveTo -> {
                    val (x, y) = drawPath as DrawPath.MoveTo
                    path.moveTo(x, y)
                }

                is DrawPath.CurveTo -> {
                    val (prevX, prevY, x, y) = drawPath as DrawPath.CurveTo
                    path.quadraticBezierTo(prevX, prevY, (x + prevX) / 2, (y + prevY) / 2)
                }

                else -> {
                    return@Canvas
                }
            }

            drawPath(
                path = path,
                color = Color.Blue,
                style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }

        Row( modifier = modifier.padding(8.dp)) {
            Button(onClick = {
                println("Button was clicked")
                println(inkBuilder.build())
                drawViewModel.digitalInkModel.recognize_ink(inkBuilder.build())


            }) {
                Text("Save")
            }
            Button( onClick = {
                println("Clear button was clicked")
                drawViewModel.clear_ink()
            }) {
                Text("Clear")
            }
        }


    }
}

