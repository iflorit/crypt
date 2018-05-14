package es.florit.crypt.datasource.repository.dagger

import dagger.Component
import es.florit.crypt.ui.base.BasePresenter
import javax.inject.Singleton

/**
 * Created by ismael on 19/3/18.
 */
@Singleton
@Component(modules = arrayOf(CryptModule::class))
interface CryptComponent {
    fun inject(presenter: BasePresenter) {}
}
