package es.florit.crypt.ui.list

import es.florit.crypt.R
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.base.BasePresenter
import es.florit.crypt.ui.list.util.EndlessOnScrollListener
import java.io.IOException

/**
 * Presenter that manages all the login on this screen
 *
 * Created by ismael on 18/3/18.
 */
class CrytoListPresenter(val mView: ICryptoListView) : BasePresenter() {

    private val TAG = "CrytoListPresenter"

    val mCryptoList: MutableList<Crypto> by lazy { arrayListOf<Crypto>() }


    override fun onCreate() {
        super.onCreate()

        mView.getEndlessListener().isLoading = false
    }

    override fun onResume() {
        if (mCryptoList.isEmpty()) {
            mView.getEndlessListener().onLoadMore(0)
        }
    }

    /**
     * MVP interface that should follow the activity/fragment
     */
    interface ICryptoListView {
        /** returns the scroll tracker */
        fun getEndlessListener(): EndlessOnScrollListener

        /** sets the init amount of tv shows on the similars list */
        fun setCryptos(list: MutableList<Crypto>)

        /** adds one more page to the similars list */
        fun addCryptos(insertIndex: Int, insertCount: Int)

        /** reports a connection error */
        fun showInternetError()

        /** reports an empty list */
        fun showEmpty(reason: Int = R.drawable.empty)
    }

    fun onLoadMore(currentPage: Int) {
        getMore(currentPage)
    }

    /**
     * Loads more detail tvshows (should put the page that we would load)
     */
    private fun getMore(currentPage: Int) {
        // TODO cancelCall()
        mView.getEndlessListener().isLoading = true
        //mView.addFooter(HustlerListAdapter.FOOTER_PROGRESS);

        cryptoRepository.getCryptos(
                page = currentPage + 1,
                onSuccess = { result ->
                    // what to do after load more results
                    mView.getEndlessListener().isLoading = false

                    if (mCryptoList.isEmpty()) {
                        mCryptoList.addAll(result)
                        mView.setCryptos(mCryptoList)
                    } else if (!result.isEmpty()) {
                        mCryptoList.addAll(result)
                        mView.addCryptos(mCryptoList.size - result.size, result.size)
                    }
                },
                onFailure = { result ->
                    when (result) {
                        is IOException -> mView.showInternetError()
                        else -> mView.showEmpty()
                    }
                }
        )
    }
}
