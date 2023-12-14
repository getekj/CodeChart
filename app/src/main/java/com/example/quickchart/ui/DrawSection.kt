package com.example.quickchart.ui


import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickchart.R
import com.example.quickchart.model.Intervention
import com.example.quickchart.model.InterventionsViewModel
import com.google.mlkit.vision.digitalink.Ink
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DrawSection(
    modifier: Modifier = Modifier,
    drawViewModel: DrawSectionViewModel,
//    interventionViewModel: InterventionsViewModel
    addIntervention: (String, String) -> Unit,
    interventions: List<Intervention>
) {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


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
            val current = LocalDateTime.now().format(formatter)
            //interventionViewModel.add_intervention(time, recognitionText)
            if (recognitionText != "") {
                addIntervention(current, recognitionText)
            }
        }
    }

    Row(
        modifier = Modifier
    ) {
        Column(
            modifier = modifier.padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            InterventionBar()

            Canvas(
                modifier = modifier
                    .size(550.dp, 700.dp)
                    .offset(x = 10.dp, y = 30.dp)
                    .background(colorResource(id = R.color.light_grey))
                    .border(width = 8.dp, color = colorResource(id = R.color.medium_grey))
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

            Spacer(modifier = Modifier.size(20.dp))
            
            Row(
                modifier = modifier.padding(8.dp)
            ) {
                Button(onClick = {
                    println("Button was clicked")
                    println(inkBuilder.build())
                    drawViewModel.digitalInkModel.recognize_ink(inkBuilder.build())
                    drawViewModel.clear_ink()
                },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }
                Button(onClick = {
                    println("Clear button was clicked")
                    drawViewModel.clear_ink()
                },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
                ) {
                    Text(
                        text = "Clear",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }
            }

            QuickAdd()

        }
        ProgressColumn(interventions = interventions)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionBar() {

    val interventionTextField = remember {
        mutableStateOf(TextFieldValue())
    }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center
    ) {

        TextField(
            value = interventionTextField.value,
            onValueChange = { interventionTextField.value = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Type intervention",
                    fontSize = 28.sp
                )
            },
            modifier = Modifier
                .padding(8.dp)
                .defaultMinSize(minHeight = 70.dp, minWidth = 400.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.labelMedium
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }


    }
}

@Composable
fun QuickAdd() {

    Text(
        text = "Quick Add Common Interventions",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .padding(start = 16.dp)
    )

    Row(
        modifier = Modifier.padding(4.dp)
    ) {

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
        ) {
            Text(
                text = "1 mg epi",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black
            )
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
        ) {
            Text(
                text = "Compressions",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black
            )
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.pink))
        ) {
            Text(
                text = "Pulse Check",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black
            )
        }

    }
}