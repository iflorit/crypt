package es.florit.crypt.ui.base

import android.support.v7.app.AppCompatActivity

/**
 * Basic app flow
 *
 * Created by ismael on 19/3/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        getPresenter().onResume()
    }

    protected fun initPresenter() {
        getPresenter().onCreate()
    }

    abstract fun getPresenter(): BasePresenter
}