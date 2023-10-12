package com.example.quickchart.ui

//
//
//import android.view.MotionEvent
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.StrokeJoin
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.input.pointer.pointerInteropFilter
//import androidx.compose.ui.unit.dp
//
//
///*
//*  This Section of the screen accepts touch inputs, renders them on screen, and passes the content to
//*  the StrokeManager
//*
//* */
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun DrawSection(
//    modifier: Modifier = Modifier
//) {
//
//    val path = remember { Path() }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .pointerInteropFilter {event ->
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        path.moveTo(event.x, event.y)
//                    }
//                    MotionEvent.ACTION_MOVE -> { path.lineTo(event.x, event.y) }
//                    MotionEvent.ACTION_UP -> {
//                        //path.lineTo(event.x, event.y)
//                    }
//                    else -> { /* do nothing */ }
//                }
//                true
//            }
//    ) {
//
//        drawPath(path = path, color = Color.Blue, style = Stroke(width = 5.0F))
//
//    }
//
//}




import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
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
import com.google.mlkit.vision.digitalink.Ink


sealed class DrawEvent {
    data class Down(val x: Float, val y: Float): DrawEvent()
    data class Move(val x: Float, val y: Float): DrawEvent()
    object Up: DrawEvent()
}

private sealed class DrawPath {
    data class MoveTo(val x: Float, val y: Float): DrawPath()
    data class CurveTo(val prevX: Float, val prevY: Float, val x: Float, val y: Float): DrawPath()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawSection(
    modifier: Modifier = Modifier,
    viewModel: DrawSectionViewModel
//    reset: Boolean = false,
//    onDrawEvent: (DrawEvent) -> Unit,
) {

    val path = remember { Path() }

    var drawPath by remember { mutableStateOf<DrawPath?>(null) }
    var drawEvent by remember {
        mutableStateOf<DrawEvent?>(null)
    }
    var inkBuilder = Ink.builder()
    lateinit var strokeBuilder: Ink.Stroke.Builder

//    if (reset) {
//        drawPath = null
//        path.reset()
//    }

    Canvas(
        modifier = modifier
            .size(600.dp, 900.dp)
            .offset(x = 10.dp, y= 50.dp)
            .background(Color.LightGray)
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        drawPath = DrawPath.MoveTo(event.x, event.y)
                        //onDrawEvent.invoke(DrawEvent.Down(event.x, event.y))
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
                        //onDrawEvent.invoke(DrawEvent.Move(event.x, event.y))
                        DrawEvent.Move(event.x, event.y)

                        strokeBuilder!!.addPoint(Ink.Point.create(event.x, event.y))
                    }

                    MotionEvent.ACTION_UP -> {
//                        onDrawEvent.invoke(DrawEvent.Up)
                        DrawEvent.Up

                        strokeBuilder.addPoint(Ink.Point.create(event.x, event. y))
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
                path.quadraticBezierTo(prevX, prevY, (x + prevX)/2, (y + prevY)/2)
            }

            else -> {return@Canvas}
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

    Button( onClick = {
        println("Button was clicked")
        println(inkBuilder.build())
        viewModel.recognize_ink(inkBuilder.build())
    }) {
        Text("CLICK ME")
    }
}

////
////@SuppressLint("ResourceAsColor")
////class DrawSection(context: Context?, attrs:AttributeSet): View(context, attrs) {
////
////    private val currentStrokePaint:Paint = Paint()
////
////    private val canvasPaint:Paint
////
////    private val currentStroke:Path
////
////    private var drawCanvas: Canvas? = null
////
////    private var canvasBitmap: Bitmap?= null
////
////    private val STROKE_WIDTH_DP = 8.0
////
////    init {
////
////        currentStrokePaint.color = R.color.black
////        currentStrokePaint.isAntiAlias=true
////        currentStrokePaint.strokeWidth= STROKE_WIDTH_DP.toFloat()
////        currentStrokePaint.style = Paint.Style.STROKE
////        currentStrokePaint.strokeJoin = Paint.Join.ROUND
////        currentStrokePaint.strokeCap = Paint.Cap.ROUND
////
////        currentStroke = Path()
////
////        canvasPaint = Paint(Paint.DITHER_FLAG)
////    }
////
////    fun OnSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
////
////        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
////
////        drawCanvas = Canvas(canvasBitmap!!)
////
////        invalidate()
////
////    }
////
////    override fun onDraw(canvas: Canvas) {
////
////        canvas.drawBitmap(canvasBitmap!!,0f,0f,canvasPaint)
////
////        canvas.drawPath(currentStroke, currentStrokePaint)
////
////
////    }
////
////    fun OnTouchEvent(event: MotionEvent): Boolean {
////
////        val action= event.actionMasked
////
////        val x = event.x
////        val y = event.y
////
////        when (action) {
////
////            MotionEvent.ACTION_DOWN->currentStroke.moveTo(x,y)
////            MotionEvent.ACTION_MOVE->currentStroke.lineTo(x,y)
////            MotionEvent.ACTION_UP-> {
////
////                currentStroke.lineTo(x,y)
////                drawCanvas!!.drawPath(currentStroke, currentStrokePaint)
////                currentStroke.reset()
////
////            }
////            else->{
////            }
////
////        }
////        StrokeManager.addNewTouchEvent(event)
////
////        invalidate()
////        return true
////    }
////
////    fun clear() {
////        onSizeChanged(
////            canvasBitmap!!.width,
////            canvasBitmap!!.height,
////            canvasBitmap!!.width,
////            canvasBitmap!!.height,
////        )
////    }
////
////}