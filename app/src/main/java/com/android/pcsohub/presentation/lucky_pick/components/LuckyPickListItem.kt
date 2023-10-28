package com.android.pcsohub.presentation.lucky_pick.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.pcsohub.R

@Composable
fun LuckyPickListItem(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    onGenerate: () -> Unit,
    lotto: String
) {
    Card(modifier = modifier.padding(4.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = icon, contentDescription = "Lotto Icon", modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(onClick = onGenerate) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_shuffle_24),
                        contentDescription = "Generate"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedBallDigit(
                modifier = Modifier.align(Alignment.CenterHorizontally), lotto = lotto
            )
        }
    }

}