package com.android.pcsohub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LottoEntity::class, LuckyPickEntity::class], version = 1, exportSchema = false
)
abstract class LottoDatabase : RoomDatabase() {
    abstract val lottoDao: LottoDao

    abstract val luckyPickDao: LuckyPickDao
}