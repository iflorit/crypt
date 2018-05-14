package es.florit.crypt.dagger

import dagger.Component
import es.florit.crypt.CryptoApplication
import es.florit.crypt.datasource.repository.dagger.CryptModule
import es.florit.crypt.ui.detail.dagger.CryptoDetailComponent
import es.florit.crypt.ui.detail.dagger.CryptoDetailModule
import es.florit.crypt.ui.list.dagger.CryptoListComponent
import es.florit.crypt.ui.list.dagger.CryptoListModule
import javax.inject.Singleton

/**
 * Created by ismael on 19/3/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class, CryptModule::class))
interface AppComponent {
    fun inject(app: CryptoApplication)

    fun plus(module: CryptoListModule): CryptoListComponent

    fun plus(module: CryptoDetailModule): CryptoDetailComponent

    fun plus(module: CryptModule): CryptModule
}