package com.gonativecoders.whosin

import android.app.Application
import com.gonativecoders.whosin.core.data.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WhosInApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@WhosInApplication)
            modules(koinModules, dataModule)
        }
    }

}