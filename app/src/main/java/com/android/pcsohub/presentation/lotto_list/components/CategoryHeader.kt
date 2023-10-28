package com.android.pcsohub.presentation.lotto_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryHeader(
    date: String, modifier: Modifier = Modifier
) {
    Text(
        text = date,
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        style = MaterialTheme.typography.titleMedium
    )
}