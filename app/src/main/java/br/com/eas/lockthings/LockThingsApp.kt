package br.com.eas.lockthings

import android.app.Application

import br.com.eas.lockthings.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LockThingsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@LockThingsApp)
            modules(
                listOf(viewModelModule)
            )
        }
    }
}