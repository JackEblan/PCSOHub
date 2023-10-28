package com.android.pcsohub.presentation.lotto_list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.android.pcsohub.R
import com.android.pcsohub.common.UiText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BallDigit(
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
                            digit,
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
            text = UiText.StringResource(R.string.invalid).asString(),
            color = MaterialTheme.colorScheme.error,
            style = digitStyle
        )
    }
}