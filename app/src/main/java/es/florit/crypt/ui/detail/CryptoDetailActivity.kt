package es.florit.crypt.ui.detail

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import com.github.mikephil.charting.data.Entry
import es.florit.crypt.R
import es.florit.crypt.app
import es.florit.crypt.datasource.repository.model.Crypto
import es.florit.crypt.ui.base.BaseActivity
import es.florit.crypt.ui.base.BasePresenter
import es.florit.crypt.ui.detail.dagger.CryptoDetailModule
import es.florit.crypt.ui.list.CrytoListActivity
import kotlinx.android.synthetic.main.activity_crypto_detail.*
import javax.inject.Inject

/**
 * An activity representing a single TVShow detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [CrytoListActivity].
 */
class CryptoDetailActivity : BaseActivity(), CryptoDetailPresenter.ICryptoDetailView {

    val component by lazy { app.component.plus(CryptoDetailModule(this)) }

    @Inject
    lateinit var mPresenter: CryptoDetailPresenter

    lateinit private var fragment: CryptoDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_detail)

        setSupportActionBar(detail_toolbar)

        // Crypto the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        restoreFragment(savedInstanceState)

        component.inject(this)

        initPresenter()
    }

    private fun restoreFragment(savedInstanceState: Bundle?) {

        val extra: Crypto = intent.getParcelableExtra(CryptoDetailFragment.ARG_ITEM)
                ?: savedInstanceState?.getParcelable(CryptoDetailFragment.ARG_ITEM)!!

        fragment = CryptoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CryptoDetailFragment.ARG_ITEM, extra)
            }
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.crypto_detail_container, fragment)
                .commit()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)


        restoreFragment(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.putAll(intent.extras)
    }

    override fun getPresenter(): BasePresenter = mPresenter

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, CrytoListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }


    override fun setEntries(entries: ArrayList<Entry>) {
        fragment.setEntries(entries)
    }
}
