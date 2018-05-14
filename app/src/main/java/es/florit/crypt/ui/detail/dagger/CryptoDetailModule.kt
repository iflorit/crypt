package es.florit.crypt.ui.detail.dagger

import dagger.Module
import dagger.Provides
import es.florit.crypt.ui.detail.CryptoDetailActivity
import es.florit.crypt.ui.detail.CryptoDetailPresenter

/**
 * Created by ismael on 19/3/18.
 */
@Module
class CryptoDetailModule(val activity: CryptoDetailActivity) {

    @Provides
    fun providePresenter(): CryptoDetailPresenter {
        return CryptoDetailPresenter(activity)
    }
}