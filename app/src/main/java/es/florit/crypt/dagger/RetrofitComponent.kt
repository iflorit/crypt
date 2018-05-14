package es.florit.crypt.dagger

import dagger.Component
import es.florit.crypt.datasource.repository.CryptRepository
import javax.inject.Singleton


/**
 * Created by ismael on 19/3/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class))
interface RetrofitComponent {

    fun inject(repository: CryptRepository)
}