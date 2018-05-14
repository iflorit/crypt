package es.florit.crypt.datasource.repository.mapper

import es.florit.crypt.datasource.api.model.CryptoResponse
import es.florit.crypt.datasource.repository.model.Crypto

/**
 * Maps the api object to our UI layer object
 *
 * Should not have nulls or empty values, but the remote is not too strict, so this is
 * because I've provided <code>?: ""</code>. Almost, the null or empty data is not on the main
 * fields (id and name)
 *
 * Created by ismael on 19/3/18.
 */
object ResponseMapper {

    fun mapToCrypto(): (CryptoResponse) -> Crypto = {
        Crypto(
                it.id,
                it.name,
                it.usd,
                it.change1h
        ).apply {
            // optional logo field
            this.logo ?: ""
        }
    }
}