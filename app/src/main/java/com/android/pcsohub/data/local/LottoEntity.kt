package com.android.pcsohub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LottoEntity(
    @PrimaryKey val id: String,
    val contentBadge: String,
    val contentBody: String,
    val contentData1: Int,
    val contentData2: String,
    val contentId: String,
    val contentPhoto: String? = null,
    val contentSubtitle1: String,
    val contentSubtitle2: String,
    val contentTitle: String,
    val contentUrl: String
)
