package es.florit.crypt.datasource.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Datamodel part of CryptoHistoricalResponse
 *
 * Created by ismael on 17/3/18.
 */
data class CryptoSnapshotResponse(
        @SerializedName("price_usd") val usd: Double,
        @SerializedName("snapshot_at") val snapshot: Date //"2018-05-03T08:54:02+00:00"

)