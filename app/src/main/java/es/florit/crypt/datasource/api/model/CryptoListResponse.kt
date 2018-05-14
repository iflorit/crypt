package es.florit.crypt.datasource.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ismael on 17/3/18.
 */
data class CryptoListResponse(

        @SerializedName("coins") val obj : CryptoList
)

data class CryptoList(
        @SerializedName("total") val total: Int,
        @SerializedName("per_page") val items: Int,
        @SerializedName("current_page") val current: Int,
        @SerializedName("last_page") val last: Int,
        @SerializedName("first_page_url") val first_url: String,
        @SerializedName("last_page_url") val last_url: String,
        @SerializedName("next_page_url") val next_url: String,
        @SerializedName("prev_page_url") val prev_url: String,
        @SerializedName("path") val path: String, // "http://dev.cryptos.com/coins",
        @SerializedName("from") val from: Int,
        @SerializedName("to") val to: Int,

        @SerializedName("data") val data: List<CryptoResponse>
)