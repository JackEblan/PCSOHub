package com.android.pcsohub.presentation.lucky_pick

data class LuckPickState(
    val ultraLotto: String = "00-00-00-00-00-00",
    val grandLotto: String = "00-00-00-00-00-00",
    val superLotto: String = "00-00-00-00-00-00",
    val megaLotto: String = "00-00-00-00-00-00",
    val lotto: String = "00-00-00-00-00-00",
    val sixD: String = "00-00-00-00-00-00",
    val fourD: String = "00-00-00-00",
    val threeD: String = "00-00-00",
    val twoD: String = "00-00"
)
