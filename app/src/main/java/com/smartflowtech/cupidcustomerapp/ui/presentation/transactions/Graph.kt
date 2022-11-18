package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.domain.Period
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun Graph(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int,
    periodFilter: String,
    cardHistoryPeriodFilterContext: CardHistoryPeriodFilterContext
) {
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    val markerPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 18.sp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size
            /** placing x axis points */
            for (i in xValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    when(cardHistoryPeriodFilterContext) {
                        CardHistoryPeriodFilterContext.DEFAULT -> {
                            when (periodFilter) {
                                Period.ONE_WEEK.name -> {
                                    when (xValues[i]) {
                                        1 -> "Mon"
                                        2 -> "Tue"
                                        3 -> "Wed"
                                        4 -> "Thu"
                                        5 -> "Fri"
                                        6 -> "Sat"
                                        7 -> "Sun"
                                        else -> ""
                                    }
                                }
                                Period.ONE_MONTH.name -> {
                                    if (xValues[i] % 2 == 0) {
                                        "${xValues[i]}"
                                    } else {
                                        ""
                                    }
                                }
                                else -> {
                                    when (xValues[i]) {
                                        1 -> "Jan"
                                        2 -> ""
                                        3 -> "Mar"
                                        4 -> ""
                                        5 -> "May"
                                        6 -> ""
                                        7 -> "Jul"
                                        8 -> ""
                                        9 -> "Sep"
                                        10 -> ""
                                        11 -> "Nov"
                                        12 -> ""
                                        else -> ""
                                    }
                                }
                            }
                        }
                        CardHistoryPeriodFilterContext.MONTH_YEAR -> {
                            if (xValues[i] % 2 == 0) {
                                "${xValues[i]}"
                            } else {
                                ""
                            }
                        }
                    }
                    ,
                    xAxisSpace * (i + 1),
                    size.height + 100,
                    textPaint
                )
            }

            for (i in xValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    ".",
                    xAxisSpace * (i + 1),
                    size.height + 50,
                    markerPaint
                )
            }

            /** placing y axis points */
            for (i in yValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    when (yValues[i]) {
                        in 1_000_000..999_999_999 -> "₦${yValues[i].toString().dropLast(6)}M - "
                        in 1000..999_999 -> "₦${yValues[i].toString().dropLast(3)}K - "
                        in 100..999 -> "₦${yValues[i].toString().dropLast(2)}H - "
                        else -> "₦${Util.formatAmount(yValues[i])} - "
                    },
                    paddingSpace.toPx() / 2f,
                    size.height - yAxisSpace * (i + 1),
                    textPaint
                )
            }
            /** placing our x axis points */
            coordinates.clear()
            for (i in points.indices) {
                val x1 = xAxisSpace * (i + 1)
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                coordinates.add(PointF(x1, y1))
                if (points[i] != 0f) {
                    /** drawing circles to indicate all the points */
                    drawCircle(
                        color = Color.Red,
                        radius = 5f,
                        center = Offset(x1, y1)
                    )
                }

            }
            /** calculating the connection points */
            controlPoints1.clear()
            controlPoints2.clear()
            for (i in 1 until coordinates.size) {
                controlPoints1.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i - 1].y
                    )
                )
                controlPoints2.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i].y
                    )
                )
            }
            /** drawing the path */
            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 0 until coordinates.size - 1) {
                    cubicTo(
                        controlPoints1[i].x, controlPoints1[i].y,
                        controlPoints2[i].x, controlPoints2[i].y,
                        coordinates[i + 1].x, coordinates[i + 1].y
                    )
                }
            }

            /** filling the area under the path */
            val fillPath = android.graphics.Path(stroke.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(xAxisSpace * xValues.last(), size.height - yAxisSpace)
                    lineTo(xAxisSpace, size.height - yAxisSpace)
                    close()
                }
            drawPath(
                fillPath,
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Transparent,
                    ),
                    endY = size.height - yAxisSpace
                ),
            )
            drawPath(
                stroke,
                color = darkBlue,
                style = Stroke(
                    width = 2f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}