package es.florit.crypt.datasource.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ismael on 17/3/18.
 */
data class CryptoDetailResponse(
        @SerializedName("coin") val coin: CryptoResponse
)