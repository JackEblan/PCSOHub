package com.android.pcsohub.presentation.lotto_list.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.pcsohub.R
import com.android.pcsohub.common.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LottoDatePicker(
    datePickerState: DatePickerState,
    openDatePicker: Boolean,
    onSearch: (Long) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
    confirmEnabled: Boolean
) {
    if (openDatePicker) {
        DatePickerDialog(onDismissRequest = {
            onDismiss()
        }, confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { onSearch(it) }
                }, enabled = confirmEnabled
            ) {
                Text(UiText.StringResource(R.string.search).asString())
            }
        }, dismissButton = {
            TextButton(onClick = {
                onReset()
            }) {
                Text(UiText.StringResource(R.string.reset).asString())
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }
}