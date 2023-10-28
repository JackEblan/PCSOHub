package com.android.pcsohub.presentation.lotto_list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.pcsohub.domain.model.GroupedLotto
import com.android.pcsohub.domain.model.Lotto
import com.android.pcsohub.presentation.components.FloatingActionButtonHideOnScroll
import com.android.pcsohub.presentation.lotto_list.components.CategoryHeader
import com.android.pcsohub.presentation.lotto_list.components.LottoDatePicker
import com.android.pcsohub.presentation.lotto_list.components.LottoListItem
import com.android.pcsohub.ui.theme.PCSOBallAPITheme
import java.time.LocalDate
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LottoListScreen(
    modifier: Modifier = Modifier, viewModel: LottoListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.state.collectAsState().value

    val listState = rememberLazyListState()

    val visibilityState by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    var openDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = utcTimeMillis

            val selectedYear = calendar.get(Calendar.YEAR)
            val selectedMonth = calendar.get(Calendar.MONTH) + 1
            val selectedDay = calendar.get(Calendar.DAY_OF_MONTH)

            if (LocalDate.of(selectedYear, selectedMonth, selectedDay) > LocalDate.now()) {
                return false
            }

            return true
        }
    })

    val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    LaunchedEffect(key1 = true) {
        viewModel.snackbarFlow.collect { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LottoDatePicker(datePickerState = datePickerState, openDatePicker = openDatePicker, onSearch = {
        viewModel.onEvent(
            LottoListEvent.GetLottoByDate(it)
        )

        openDatePicker = false
    }, onReset = {
        viewModel.onEvent(
            LottoListEvent.GetLottoResults
        )

        openDatePicker = false
    }, onDismiss = {

        openDatePicker = false
    }, confirmEnabled = confirmEnabled.value
    )

    StatelessScreen(modifier = modifier,
                    state = state,
                    listState = listState,
                    fabVisibility = visibilityState,
                    onFabClick = {
                        openDatePicker = true
                    })
}

@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    state: LottoListState,
    listState: LazyListState,
    fabVisibility: Boolean,
    onFabClick: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(), state = listState
            ) {
                state.categories.forEach { category ->
                    item {
                        CategoryHeader(date = category.date)
                    }

                    items(category.lotto) {
                        LottoListItem(lotto = it)
                    }
                }
            }
        }

        FloatingActionButtonHideOnScroll(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            visibilityState = fabVisibility,
            onClick = onFabClick,
        ) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Picker")
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    PCSOBallAPITheme {
        Surface {
            StatelessScreen(state = LottoListState(isLoading = false,
                                                   categories = List(20) { outerIndex ->
                                                       GroupedLotto(date = "Header $outerIndex",
                                                                    lotto = List(20) { innerIndex ->
                                                                        Lotto(
                                                                            contentBadge = "$innerIndex",
                                                                            contentBody = "$innerIndex",
                                                                            contentPhoto = "$innerIndex",
                                                                            contentSubtitle1 = "$innerIndex",
                                                                            contentSubtitle2 = "$innerIndex",
                                                                            contentTitle = "$innerIndex"
                                                                        )
                                                                    })
                                                   }),
                            listState = rememberLazyListState(),
                            fabVisibility = true,
                            onFabClick = {})
        }
    }
}