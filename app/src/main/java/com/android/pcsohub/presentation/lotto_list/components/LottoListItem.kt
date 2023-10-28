package com.android.pcsohub.presentation.lotto_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.pcsohub.domain.model.Lotto
import com.android.pcsohub.ui.theme.PCSOHubTheme

@Composable
fun LottoListItem(
    lotto: Lotto, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = lotto.contentPhoto,
                contentDescription = "Lotto Icon",
                modifier = Modifier
                    .size(100.dp)
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = lotto.contentTitle, style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                BallDigit(
                    lotto = lotto.contentBody, modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "₱${lotto.contentSubtitle2}")

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "${lotto.contentBadge} winners")
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    PCSOHubTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LottoListItem(
                lotto = Lotto(
                    contentBadge = "89",
                    contentBody = "16-89-70-90",
                    contentPhoto = "photo",
                    contentSubtitle1 = "September 22, 2023 (Friday)",
                    contentSubtitle2 = "49,000,000",
                    contentTitle = "2D Lotto 9PM"
                )
            )
        }
    }
}