package com.android.pcsohub.domain.repository

import com.android.pcsohub.data.local.LuckyPickEntity
import kotlinx.coroutines.flow.Flow

interface LuckyPickRepository {

    suspend fun insertLuckyPick(lotto: List<LuckyPickEntity>)
    fun getLuckyPick(): Flow<List<LuckyPickEntity>>
}