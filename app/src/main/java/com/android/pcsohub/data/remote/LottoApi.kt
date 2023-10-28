package com.android.pcsohub.data.remote

import com.android.pcsohub.data.remote.dto.LottoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LottoApi {

    @GET("latest")
    suspend fun getLotto(): LottoDto

    @GET("date/{date}")
    suspend fun getLottoByDate(@Path("date") date: String): LottoDto

    companion object {
        const val BASE_URL = "https://pcsoball.com/api/"
    }
}