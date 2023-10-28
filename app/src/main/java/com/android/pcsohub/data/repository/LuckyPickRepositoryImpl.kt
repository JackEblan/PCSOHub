package com.android.pcsohub.data.repository

import com.android.pcsohub.data.local.LottoDatabase
import com.android.pcsohub.data.local.LuckyPickEntity
import com.android.pcsohub.domain.repository.LuckyPickRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LuckyPickRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val lottoDb: LottoDatabase
) : LuckyPickRepository {
    override suspend fun insertLuckyPick(lotto: List<LuckyPickEntity>) {
        withContext(ioDispatcher) {
            lottoDb.luckyPickDao.insertLuckyPick(lotto)
        }
    }

    override fun getLuckyPick(): Flow<List<LuckyPickEntity>> {
        return lottoDb.luckyPickDao.getLuckyPick()
    }
}