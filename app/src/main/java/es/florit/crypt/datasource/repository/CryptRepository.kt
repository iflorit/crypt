package es.florit.crypt.datasource.repository

import android.util.Log
import es.florit.crypt.dagger.DaggerRetrofitComponent
import es.florit.crypt.dagger.RetrofitComponent
import es.florit.crypt.dagger.RetrofitModule
import es.florit.crypt.datasource.api.model.*
import es.florit.crypt.datasource.api.service.CryptService
import es.florit.crypt.datasource.repository.mapper.ResponseMapper
import es.florit.crypt.datasource.repository.model.Crypto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Manager that implements the repository pattern.
 *
 * In this example, there is only one data source so the implementation should be a little
 * useless, but usefull in next steps.
 *
 * Created by ismael on 17/3/18.
 */
class CryptRepository() {

    val component: RetrofitComponent by lazy {
        DaggerRetrofitComponent
                .builder()
                .retrofitModule(RetrofitModule())
                .build()
    }

    init {
        // do the injection and get an instance of the api service
        component.inject(this)
    }

    @Inject
    lateinit var api: CryptService

    private val TAG: String = "REPO::"

    /**
     * Returns a page of the remote endpoint
     */
    fun getCryptos(page: Int = 1,
                   onSuccess: (List<Crypto>) -> Unit,
                   onFailure: (Throwable?) -> Unit = { error -> Log.w("${TAG} getCryptos", error.toString()) }) {


        // load from remote
        val call = api.coins(page)
        requestCryptos(call, onSuccess, onFailure)
    }

    /**
     * Returns a page of the remote endpoint for details
     */
    fun getDetail(coin: Int,
                  onSuccess: (Crypto) -> Unit,
                  onFailure: (Throwable?) -> Unit = { error -> Log.w("${TAG} getDetail", error.toString()) }) {

        // load from remote
        val call = api.detail(coin)
        requestDetail(call, onSuccess, onFailure)
    }

    /**
     * Returns the history of the current currency
     */
    fun getHistory(id: Int,
                   onSuccess: (List<CryptoSnapshotResponse>) -> Unit,
                   onFailure: (Throwable?) -> Unit = { error -> Log.w("${TAG} getHistory", error.toString()) }) {
        val call = api.historical(id)
        requestHistorical(call = call, onSuccess = onSuccess, onFailure = onFailure)
    }

    private fun requestDetail(call: Call<CryptoDetailResponse>, onSuccess: (Crypto) -> Unit, onFailure: (Throwable?) -> Unit) {
        call.enqueue(object : Callback<CryptoDetailResponse> {
            override fun onFailure(call: Call<CryptoDetailResponse>?, t: Throwable?) {

                // load from local if possible
                loadCryptos().takeIf { it.size > 0 }
                        ?.let {
                            it //.mapNotNull(ResponseMapper.mapToCrypto())
                                    .first()
                                    .let { found ->
                                        onSuccess(found)
                                    }
                        }
                        ?: onFailure(t)

            }

            override fun onResponse(call: Call<CryptoDetailResponse>?, response: Response<CryptoDetailResponse>?) {
                val crypto = response?.body()?.coin//.mapNotNull(ResponseMapper.mapToCrypto())
                // ?: listOf()

                // TODO store locally for offline purposes

                crypto?.let {
                    onSuccess(arrayListOf<CryptoResponse>().apply { add(it) }
                            .mapNotNull(ResponseMapper.mapToCrypto())
                            .first()
                    )
                } ?: onFailure(Throwable())

            }
        })
    }

    /**
     * Requests and parses some endpoints (in this case, tv/populars and tv/similars. Both with
     * the same data structure)
     */
    private fun requestCryptos(call: Call<CryptoListResponse>, onSuccess: (List<Crypto>) -> Unit, onFailure: (Throwable?) -> Unit) {
        call.enqueue(object : Callback<CryptoListResponse> {
            override fun onFailure(call: Call<CryptoListResponse>?, t: Throwable?) {

                // load from local if possible
                loadCryptos().takeIf { it.size > 0 }
                        ?.let { onSuccess(it) }
                        ?: onFailure(t)

            }

            override fun onResponse(call: Call<CryptoListResponse>?, response: Response<CryptoListResponse>?) {
                val cryptos: List<Crypto> = response?.body()?.obj?.data?.mapNotNull(ResponseMapper.mapToCrypto())
                        ?: listOf()

                // TODO store locally for offline purposes

                onSuccess(cryptos)
            }
        })
    }

    /**
     * load from local
     */
    private fun loadCryptos(id: Int = -1): List<Crypto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun requestHistorical(call: Call<CryptoHistoricalResponse>, onSuccess: (List<CryptoSnapshotResponse>) -> Unit, onFailure: (Throwable?) -> Unit) {
        call.enqueue(object : Callback<CryptoHistoricalResponse> {
            override fun onFailure(call: Call<CryptoHistoricalResponse>?, t: Throwable?) {
                onFailure(t)
            }

            override fun onResponse(call: Call<CryptoHistoricalResponse>?, response: Response<CryptoHistoricalResponse>?) {
                val cryptos: List<CryptoSnapshotResponse>? = response?.body()?.history

                cryptos?.let(onSuccess) ?: onFailure(Throwable())
            }
        })
    }
}
