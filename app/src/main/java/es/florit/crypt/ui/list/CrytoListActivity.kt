package es.florit.crypt.ui.list

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import es.florit.crypt.R
import es.florit.crypt.app
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.base.BaseActivity
import es.florit.crypt.ui.base.BasePresenter
import es.florit.crypt.ui.list.dagger.CryptoListModule
import es.florit.crypt.ui.list.util.EndlessOnScrollListener
import es.florit.crypt.ui.list.util.CryptoListAdapter
import kotlinx.android.synthetic.main.activity_crypto_list.*
import kotlinx.android.synthetic.main.tvshow_list.*
import javax.inject.Inject

class CrytoListActivity : BaseActivity(), CrytoListPresenter.ICryptoListView {

    val component by lazy { app.component.plus(CryptoListModule(this)) }

    @Inject
    lateinit var mPresenter: CrytoListPresenter

    lateinit var mEndlessListener: EndlessOnScrollListener

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_list)

        component.inject(this)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (crypto_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        initViews()
        initPresenter()
    }

    override fun getPresenter(): BasePresenter = mPresenter

    private fun initViews() {
        mEndlessListener = object : EndlessOnScrollListener(LinearLayoutManager(this@CrytoListActivity)) {
            override fun onLoadMore(currentPage: Int) {
                mPresenter.onLoadMore(currentPage)
            }
        }

        crypto_list.addOnScrollListener(mEndlessListener)

        retry.setOnClickListener({ b ->
            mPresenter.onResume()
        })
    }

    override fun getEndlessListener() = mEndlessListener


    override fun setCryptos(list: MutableList<Crypto>) {
        crypto_list.setHasFixedSize(true)
        crypto_list.setLayoutManager(GridLayoutManager(this@CrytoListActivity, 1))
        crypto_list.adapter = CryptoListAdapter(this, list, mTwoPane)

        crypto_list.visibility = View.VISIBLE
        empty.visibility = View.GONE
    }

    override fun addCryptos(insertIndex: Int, insertCount: Int) {
        crypto_list.adapter.notifyItemRangeInserted(insertIndex, insertCount);
    }

    override fun showInternetError() {
        showEmpty(R.drawable.offline)
    }

    override fun showEmpty(emptyReason: Int) {
        crypto_list.visibility = View.GONE
        empty.visibility = View.VISIBLE
        Glide.with(this).load(emptyReason).into(iv_empty)
    }

}
