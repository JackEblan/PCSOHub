package com.android.pcsohub.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LottoDao {
    @Upsert
    suspend fun insertLotto(lotto: List<LottoEntity>)

    @Query("SELECT * FROM LottoEntity")
    suspend fun getLotto(): List<LottoEntity>

    @Query("SELECT * FROM LottoEntity WHERE contentSubtitle1 = :date")
    suspend fun getLottoByDate(date: String): List<LottoEntity>
}