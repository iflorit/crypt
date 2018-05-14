package es.florit.crypt.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.mikephil.charting.data.Entry
import es.florit.crypt.R
import es.florit.crypt.app
import es.florit.crypt.dagger.RetrofitModule
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.detail.dagger.CryptoDetailModule
import es.florit.crypt.ui.list.util.EndlessOnScrollListener
import kotlinx.android.synthetic.main.activity_crypto_detail.*
import kotlinx.android.synthetic.main.crypto_detail.*
import kotlinx.android.synthetic.main.crypto_detail.view.*
import javax.inject.Inject
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData





/**
 * A fragment representing a single crypto detail screen.
 * This fragment is either contained in a [CryptoListActivity]
 * in two-pane mode (on tablets) or a [CryptoDetailActivity]
 * on handsets.
 */
class CryptoDetailFragment : Fragment(), CryptoDetailPresenter.ICryptoDetailView {

    companion object {
        /**
         * arg item name for pass the Crypto object between activities
         */
        const val ARG_ITEM = "item"
    }

    val component by lazy { activity?.app?.component?.plus(CryptoDetailModule(activity as CryptoDetailActivity)) }

    @Inject
    lateinit var mPresenter: CryptoDetailPresenter

    lateinit private var mItem: Crypto
    lateinit private var mEndlessListener: EndlessOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                mItem = it.getParcelable(ARG_ITEM)

                activity?.let {
                    it.toolbar_layout?.title = mItem.name

                    it.background_toolbar?.let { backdrop ->
                        Glide.with(it).load("${RetrofitModule.BASE_IMG}${mItem.logo}")
                                .into(backdrop)
                    };
                }
            }
        }

        component?.inject(this)
        mPresenter.onCreate()
        mPresenter.setItem(mItem)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.crypto_detail, container, false)

        mItem?.let { crypto ->
            // "crypto" not required, but clarifies the code

            rootView.name.text = crypto.name
            rootView.overview.text = "USD " + mItem.price + " (" + mItem.percentage + ")"
            rootView.rating.rating = crypto.rank.toFloat()

            activity?.let {
                // only starts the image download with a valid activity
                Glide.with(it).load("${RetrofitModule.BASE_IMG}${mItem.logo}")
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.poster_placeholder)
                                .centerCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(rootView.poster)
            }
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()

        mPresenter.onResume()
    }

    override fun setEntries(entries: ArrayList<Entry>) {
        val dataSet = LineDataSet(entries, "Historical")
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate() // refresh
    }
}
