package es.florit.crypt.ui.list.dagger

import dagger.Module
import dagger.Provides
import es.florit.crypt.ui.list.CrytoListActivity
import es.florit.crypt.ui.list.CrytoListPresenter

/**
 * Created by ismael on 19/3/18.
 */
@Module
class CryptoListModule(val activity: CrytoListActivity) {

    @Provides
    fun providePresenter(): CrytoListPresenter {
        return CrytoListPresenter(activity)
    }
}