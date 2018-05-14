package es.florit.crypt.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by ismael on 19/3/18.
 */
@Module
class AppModule(internal var mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }
}