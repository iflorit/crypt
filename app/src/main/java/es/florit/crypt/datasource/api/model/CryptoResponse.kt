package es.florit.crypt.datasource.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Datamodel part of CryptoListResponse
 *
 * Created by ismael on 17/3/18.
 */
data class CryptoResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String, // "Ethereum",
        @SerializedName("symbol") val symbol: String, // "ETH",
        @SerializedName("logo") val logo: String,
        @SerializedName("rank") val rank: Int,
        @SerializedName("price_usd") val usd: Double, //  "719.98600000",
        @SerializedName("price_btc") val btc: Double, // "0.07797240",
        @SerializedName("24h_volume_usd") val volume: Long, //3014730000,
        @SerializedName("market_cap_usd") val market: Long, //71421998446,
        @SerializedName("available_supply") val available: Long,// 99199149,
        @SerializedName("total_supply") val total: Long, //99199149,
        @SerializedName("percent_change_1h") val change1h: Double,  //"0.28000000",
        @SerializedName("percent_change_24h") val change24h: Double, // "5.52000000",
        @SerializedName("percent_change_7d") val change7d: Double, //"14.58000000",
        @SerializedName("created_at") val created: Date, // "2018-05-03T08:54:02+00:00",
        @SerializedName("updated_at") val updated: Date //"2018-05-03T08:54:02+00:00"
)