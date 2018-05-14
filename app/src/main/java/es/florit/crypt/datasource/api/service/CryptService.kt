package es.florit.crypt.datasource.api.service

import es.florit.crypt.datasource.api.model.CryptoDetailResponse
import es.florit.crypt.datasource.api.model.CryptoHistoricalResponse
import es.florit.crypt.datasource.api.model.CryptoListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service, generates the API methods
 *
 * Created by ismael on 17/3/18.
 */
interface CryptService {

    @GET("coins")
    fun coins(@Query("page") page: Int = 1): Call<CryptoListResponse>

    @GET("coins/{coin_id}")
    fun detail(@Path("coin_id") id: Int): Call<CryptoDetailResponse>

    @GET("coins/{coin_id}/historical")
    fun historical(@Path("coin_id") id: Int): Call<CryptoHistoricalResponse>
}