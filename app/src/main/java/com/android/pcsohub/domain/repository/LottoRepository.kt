package com.android.pcsohub.domain.repository

import com.android.pcsohub.common.Resource
import com.android.pcsohub.domain.model.GroupedLotto
import kotlinx.coroutines.flow.Flow

interface LottoRepository {
    suspend fun getLotto(): Flow<Resource<List<GroupedLotto>>>
    suspend fun getLottoByDate(
        dateFromApi: String, dateFromLocal: String
    ): Flow<Resource<List<GroupedLotto>>>
}