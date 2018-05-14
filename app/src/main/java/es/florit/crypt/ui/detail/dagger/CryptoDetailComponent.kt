package es.florit.crypt.ui.detail.dagger

import dagger.Subcomponent
import es.florit.crypt.dagger.AppModule
import es.florit.crypt.ui.detail.CryptoDetailActivity
import es.florit.crypt.ui.detail.CryptoDetailFragment

/**
 * Created by ismael on 19/3/18.
 */
@Subcomponent(modules = arrayOf(AppModule::class, CryptoDetailModule::class))
interface CryptoDetailComponent {
    fun inject(activity: CryptoDetailActivity)

    fun inject(fragment: CryptoDetailFragment)
}
