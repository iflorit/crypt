package es.florit.crypt.ui.list.dagger

import dagger.Subcomponent
import es.florit.crypt.dagger.RetrofitModule
import es.florit.crypt.ui.list.CrytoListActivity

/**
 * Created by ismael on 19/3/18.
 */
@Subcomponent(modules = arrayOf(RetrofitModule::class, CryptoListModule::class))
interface CryptoListComponent {
    fun inject(activity: CrytoListActivity)

}
