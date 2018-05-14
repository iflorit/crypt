package es.florit.crypt.ui.base

import es.florit.crypt.datasource.repository.CryptRepository
import es.florit.crypt.datasource.repository.dagger.DaggerCryptComponent
import es.florit.crypt.datasource.repository.dagger.CryptModule
import javax.inject.Inject

/**
 * Basic presenter flow.
 *
 * Also injects the crypto repository
 *
 * Created by ismael on 19/3/18.
 */
abstract class BasePresenter {

    @Inject
    lateinit var cryptoRepository: CryptRepository

    val component by lazy { DaggerCryptComponent
            .builder()
            .cryptModule(CryptModule())
            .build()}

    open fun onCreate() {
        component.inject(this)
    }

    abstract fun onResume()
}