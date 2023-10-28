package com.android.pcsohub.common

sealed class Screen(val route: String, val label: String) {
    object LottoList : Screen("lotto_list", "Lotto")
    object LuckyPick : Screen("lucky_pick", "Lucky Pick")
    object About : Screen("about", "About")
}

val all_screens = listOf(Screen.LottoList, Screen.LuckyPick, Screen.About)
