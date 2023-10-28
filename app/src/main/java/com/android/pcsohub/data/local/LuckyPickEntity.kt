package com.android.pcsohub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LuckyPickEntity(
    @PrimaryKey val id: Int, val lotto: String
)
