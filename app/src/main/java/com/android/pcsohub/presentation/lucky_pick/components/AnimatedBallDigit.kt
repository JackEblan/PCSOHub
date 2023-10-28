package com.android.pcsohub.presentation.lucky_pick.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimatedBallDigit(
    lotto: String,
    modifier: Modifier = Modifier,
    ballColor: Color = MaterialTheme.colorScheme.primary,
    digitStyle: TextStyle = MaterialTheme.typography.titleLarge
) {

    val pattern = "^\\d+(?:-\\d+)*$".toRegex()

    if (lotto.matches(pattern)) {
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            lotto.split("-").forEach { digit ->
                val animatedDigit by animateIntAsState(
                    targetValue = digit.toInt(), animationSpec = tween(
                        durationMillis = 5000, easing = LinearEasing
                    ), label = ""
                )

                Canvas(modifier = Modifier.size(digitStyle.fontSize.value.dp * 2)) {
                    drawCircle(
                        color = ballColor, radius = size.minDimension / 2
                    )
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = digitStyle.fontSize.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                        canvas.nativeCanvas.drawText(
                            animatedDigit.toString(),
                            size.width / 2,
                            size.height / 2 - (paint.descent() + paint.ascent()) / 2,
                            paint
                        )
                    }
                }
            }
        }
    } else {
        Text(
            text = "Invalid", color = MaterialTheme.colorScheme.error, style = digitStyle
        )
    }
}