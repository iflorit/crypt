package es.florit.crypt.ui.detail

import com.github.mikephil.charting.data.Entry
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.base.BasePresenter

/**
 * Presenter that manages all the login on this screen
 *
 * Created by ismael on 19/3/18.
 */
class CryptoDetailPresenter(val mView: ICryptoDetailView) : BasePresenter() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun onResume() {
    }

    /**
     * MVP interface that should follow the activity/fragment
     *
     * The detail interface is detail than the main list, but the
     * endless scroll tracker is used in a different way (as a secondary
     * functionality)
     */
    interface ICryptoDetailView {
        fun setEntries(entries: ArrayList<Entry>)
    }

    lateinit var mItem: Crypto

    fun setItem(item: Crypto) {
        mItem = item


        // load detail info
        cryptoRepository.getDetail(mItem.id, onSuccess = { crypto ->
            cryptoRepository.getHistory(crypto.id, onSuccess = { hist ->

                val entries = ArrayList<Entry>()

                for (snap in hist) {
                    // turn your data into Entry objects
                    entries.add(Entry(snap.snapshot.time.toFloat(), snap.usd.toFloat()))
                }

                mView.setEntries(entries)
            })
        })
    }
}