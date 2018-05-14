package es.florit.crypt.datasource.repository.dagger

import dagger.Module
import dagger.Provides
import es.florit.crypt.datasource.repository.CryptRepository

/**
 * Created by ismael on 19/3/18.
 */

@Module
open class CryptModule {

    @Provides
    open fun provideRepository(): CryptRepository {
        return CryptRepository()
    }
}