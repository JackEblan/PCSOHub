package com.android.pcsohub.presentation.lotto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pcsohub.common.Resource
import com.android.pcsohub.domain.model.GroupedLotto
import com.android.pcsohub.domain.repository.LottoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LottoListViewModel @Inject constructor(
    private val repository: LottoRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LottoListState())
    val state = _state.asStateFlow()

    private val _snackbarFlow = MutableSharedFlow<String?>()
    val snackbarFlow = _snackbarFlow.asSharedFlow()

    init {
        onEvent(LottoListEvent.GetLottoResults)
    }

    fun onEvent(event: LottoListEvent) {
        when (event) {
            is LottoListEvent.GetLottoByDate -> {
                viewModelScope.launch {
                    val date =
                        Instant.ofEpochMilli(event.timeInMillis).atZone(ZoneId.systemDefault())
                            .toLocalDate()

                    val dateFromLocal =
                        DateTimeFormatter.ofPattern("MMMM d, yyyy (EEEE)").format(date)

                    val dateFromApi = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date)

                    repository.getLottoByDate(
                        dateFromApi = dateFromApi, dateFromLocal = dateFromLocal
                    ).collectLatest(::onResult)
                }
            }

            is LottoListEvent.GetLottoResults -> viewModelScope.launch {
                repository.getLotto().collectLatest(::onResult)
            }
        }
    }

    private suspend fun onResult(result: Resource<List<GroupedLotto>>) {
        when (result) {
            is Resource.Error -> {
                _state.value = _state.value.copy(
                    isLoading = false
                )

                _snackbarFlow.emit(result.message)
            }

            is Resource.Loading -> _state.value = _state.value.copy(
                isLoading = true, categories = result.data ?: emptyList()
            )

            is Resource.Success -> _state.value = _state.value.copy(
                isLoading = false, categories = result.data ?: emptyList()
            )
        }
    }
}