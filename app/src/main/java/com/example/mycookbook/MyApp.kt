package com.example.mycookbook

import android.app.Application
import com.example.mycookbook.di.appModule
import com.example.mycookbook.di.databaseModule
import com.example.mycookbook.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule, databaseModule, networkModule)
        }
    }
}