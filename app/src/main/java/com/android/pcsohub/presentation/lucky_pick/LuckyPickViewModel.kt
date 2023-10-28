package com.android.pcsohub.presentation.lucky_pick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pcsohub.R
import com.android.pcsohub.common.UiText
import com.android.pcsohub.data.local.LuckyPickEntity
import com.android.pcsohub.domain.repository.LuckyPickRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LuckyPickViewModel @Inject constructor(private val repository: LuckyPickRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(LuckPickState())

    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    init {
        onEvent(LuckyPickEvent.GetLuckyPickList)
    }

    fun onEvent(event: LuckyPickEvent) {
        when (event) {
            LuckyPickEvent.SelectGrandLotto -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(grandLotto = lotto)
            }

            LuckyPickEvent.SelectLotto -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(lotto = lotto)
            }

            LuckyPickEvent.SelectMegaLotto -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(megaLotto = lotto)
            }

            LuckyPickEvent.SelectSuperLotto -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(superLotto = lotto)
            }

            LuckyPickEvent.SelectUltraLotto -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(ultraLotto = lotto)
            }

            LuckyPickEvent.SelectSixD -> {
                val lotto = List(6) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(sixD = lotto)
            }

            LuckyPickEvent.SelectFourD -> {
                val lotto = List(4) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(fourD = lotto)
            }

            LuckyPickEvent.SelectThreeD -> {
                val lotto = List(3) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(threeD = lotto)
            }

            LuckyPickEvent.SelectTwoD -> {
                val lotto = List(2) {
                    (0..99).random()
                }.joinToString(separator = "-")

                _state.value = _state.value.copy(twoD = lotto)
            }

            LuckyPickEvent.Save -> {
                viewModelScope.launch {
                    repository.insertLuckyPick(
                        listOf(
                            LuckyPickEntity(
                                id = 0, lotto = _state.value.ultraLotto
                            ),

                            LuckyPickEntity(
                                id = 1, lotto = _state.value.grandLotto
                            ),

                            LuckyPickEntity(
                                id = 2, lotto = _state.value.superLotto
                            ),

                            LuckyPickEntity(
                                id = 3, lotto = _state.value.megaLotto
                            ),

                            LuckyPickEntity(
                                id = 4, lotto = _state.value.lotto
                            ),

                            LuckyPickEntity(
                                id = 5, lotto = _state.value.sixD
                            ),

                            LuckyPickEntity(
                                id = 6, lotto = _state.value.fourD
                            ),

                            LuckyPickEntity(
                                id = 7, lotto = _state.value.threeD
                            ),

                            LuckyPickEntity(
                                id = 8, lotto = _state.value.twoD
                            )
                        )
                    )

                    _uiEvent.emit(
                        UIEvent.ShowSnackbar(
                            message = UiText.StringResource(R.string.successfully_saved)
                        )
                    )
                }
            }

            LuckyPickEvent.GetLuckyPickList -> {
                viewModelScope.launch {
                    repository.getLuckyPick().collectLatest { list ->
                        if (list.isNotEmpty()) {
                            _state.value = _state.value.copy(
                                ultraLotto = list[0].lotto,
                                grandLotto = list[1].lotto,
                                superLotto = list[2].lotto,
                                megaLotto = list[3].lotto,
                                lotto = list[4].lotto,
                                sixD = list[5].lotto,
                                fourD = list[6].lotto,
                                threeD = list[7].lotto,
                                twoD = list[8].lotto
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: UiText? = null) : UIEvent()
    }
}