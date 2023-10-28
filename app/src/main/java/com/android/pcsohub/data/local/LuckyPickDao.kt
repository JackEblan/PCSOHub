package com.android.pcsohub.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LuckyPickDao {
    @Upsert
    suspend fun insertLuckyPick(lotto: List<LuckyPickEntity>)

    @Query("SELECT * FROM LuckyPickEntity")
    fun getLuckyPick(): Flow<List<LuckyPickEntity>>
}