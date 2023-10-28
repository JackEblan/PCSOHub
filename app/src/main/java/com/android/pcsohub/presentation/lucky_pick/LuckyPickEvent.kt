package com.android.pcsohub.presentation.lucky_pick

sealed class LuckyPickEvent {
    object SelectUltraLotto : LuckyPickEvent()
    object SelectGrandLotto : LuckyPickEvent()
    object SelectSuperLotto : LuckyPickEvent()
    object SelectMegaLotto : LuckyPickEvent()
    object SelectLotto : LuckyPickEvent()
    object SelectSixD : LuckyPickEvent()
    object SelectFourD : LuckyPickEvent()
    object SelectThreeD : LuckyPickEvent()
    object SelectTwoD : LuckyPickEvent()
    object Save : LuckyPickEvent()
    object GetLuckyPickList : LuckyPickEvent()
}