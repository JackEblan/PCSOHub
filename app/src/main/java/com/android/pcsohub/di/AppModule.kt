package com.android.pcsohub.di

import android.content.Context
import androidx.room.Room
import com.android.pcsohub.data.local.LottoDatabase
import com.android.pcsohub.data.remote.LottoApi
import com.android.pcsohub.data.remote.LottoApi.Companion.BASE_URL
import com.android.pcsohub.data.repository.LottoRepositoryImpl
import com.android.pcsohub.data.repository.LuckyPickRepositoryImpl
import com.android.pcsohub.domain.repository.LottoRepository
import com.android.pcsohub.domain.repository.LuckyPickRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLottoDatabase(@ApplicationContext context: Context): LottoDatabase {
        return Room.databaseBuilder(
            context, LottoDatabase::class.java, "lotto.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLottoApi(): LottoApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(LottoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLottoRepository(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        lottoApi: LottoApi,
        lottoDb: LottoDatabase
    ): LottoRepository {
        return LottoRepositoryImpl(
            defaultDispatcher = defaultDispatcher, lottoApi = lottoApi, lottoDb = lottoDb
        )
    }

    @Singleton
    @Provides
    fun provideLuckyPickRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher, lottoDb: LottoDatabase
    ): LuckyPickRepository {
        return LuckyPickRepositoryImpl(ioDispatcher = ioDispatcher, lottoDb = lottoDb)
    }
}