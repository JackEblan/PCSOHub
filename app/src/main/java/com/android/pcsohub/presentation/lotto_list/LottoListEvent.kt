package com.android.pcsohub.presentation.lotto_list

sealed class LottoListEvent {
    object GetLottoResults : LottoListEvent()
    data class GetLottoByDate(val timeInMillis: Long) : LottoListEvent()
}
