package com.android.pcsohub.data.repository

import androidx.room.withTransaction
import com.android.pcsohub.common.Resource
import com.android.pcsohub.data.local.LottoDatabase
import com.android.pcsohub.data.mappers.toGroupedLottoList
import com.android.pcsohub.data.mappers.toLottoEntityList
import com.android.pcsohub.data.remote.LottoApi
import com.android.pcsohub.domain.model.GroupedLotto
import com.android.pcsohub.domain.repository.LottoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LottoRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher,
    private val lottoApi: LottoApi,
    private val lottoDb: LottoDatabase
) : LottoRepository {

    override suspend fun getLotto(): Flow<Resource<List<GroupedLotto>>> {
        return flow {
            val cacheLotto = lottoDb.lottoDao.getLotto().toGroupedLottoList(defaultDispatcher)

            emit(Resource.Loading(data = cacheLotto))

            try {
                val arrivedLotto = lottoApi.getLotto().toLottoEntityList(defaultDispatcher)

                val newCacheLotto = lottoDb.withTransaction {
                    lottoDb.lottoDao.insertLotto(arrivedLotto)
                    lottoDb.lottoDao.getLotto().toGroupedLottoList(defaultDispatcher)
                }

                emit(Resource.Success(data = newCacheLotto))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage!!))
            }
        }
    }

    override suspend fun getLottoByDate(
        dateFromApi: String, dateFromLocal: String
    ): Flow<Resource<List<GroupedLotto>>> {
        return flow {
            val cacheLotto =
                lottoDb.lottoDao.getLottoByDate(dateFromLocal).toGroupedLottoList(defaultDispatcher)

            emit(Resource.Loading(data = cacheLotto))

            try {
                val arrivedLotto =
                    lottoApi.getLottoByDate(dateFromApi).toLottoEntityList(defaultDispatcher)

                val newCacheLotto = lottoDb.withTransaction {
                    lottoDb.lottoDao.insertLotto(arrivedLotto)
                    lottoDb.lottoDao.getLottoByDate(dateFromLocal)
                        .toGroupedLottoList(defaultDispatcher)
                }

                emit(Resource.Success(data = newCacheLotto))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "An error occurred"))
            }
        }
    }
}