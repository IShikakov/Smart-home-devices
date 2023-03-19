package com.noveogroup.modulotech

import android.app.Application
import com.noveogroup.modulotech.data.di.databaseModule
import com.noveogroup.modulotech.data.di.devicesModule
import com.noveogroup.modulotech.data.di.networkModule
import com.noveogroup.modulotech.data.di.synchronizationModule
import com.noveogroup.modulotech.di.devicesListModule
import com.noveogroup.modulotech.di.resourcesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SmartHomeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@SmartHomeApplication)
            modules(
                resourcesModule,
                networkModule,
                databaseModule,
                synchronizationModule,
                devicesModule,
                devicesListModule,
            )
        }
    }
}