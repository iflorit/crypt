package es.florit.crypt

import android.app.Activity
import android.app.Application
import es.florit.crypt.dagger.AppComponent
import es.florit.crypt.dagger.AppModule
import es.florit.crypt.dagger.DaggerAppComponent

/**
 * Custom application, mainly used in this project to inject the app component
 *
 * Created by ismael on 19/3/18.
 */
open class CryptoApplication : Application() {

    open val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}


val Activity.app: CryptoApplication
    get() = application as CryptoApplication