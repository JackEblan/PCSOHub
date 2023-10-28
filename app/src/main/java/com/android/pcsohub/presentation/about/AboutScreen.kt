package com.android.pcsohub.presentation.about

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.pcsohub.BuildConfig
import com.android.pcsohub.R
import com.android.pcsohub.common.UiText
import com.android.pcsohub.ui.theme.PCSOHubTheme

@Composable
fun AboutScreen() {
    val scrollState = rememberScrollState()
    StatelessScreen(scrollState = scrollState)
}

@Composable
private fun StatelessScreen(modifier: Modifier = Modifier, scrollState: ScrollState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = R.drawable.ic_launcher_foreground,
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${
                UiText.StringResource(R.string.app_name).asString()
            }(${BuildConfig.VERSION_NAME})"
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = UiText.StringResource(R.string.about_desc).asString(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ScreenPreview() {
    PCSOHubTheme {
        Surface {
            StatelessScreen(scrollState = rememberScrollState())
        }
    }
}