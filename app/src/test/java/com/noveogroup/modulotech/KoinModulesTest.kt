package com.noveogroup.modulotech

import android.content.Context
import com.noveogroup.modulotech.data.di.databaseModule
import com.noveogroup.modulotech.data.di.devicesModule
import com.noveogroup.modulotech.data.di.networkModule
import com.noveogroup.modulotech.data.di.synchronizationModule
import com.noveogroup.modulotech.data.di.userModule
import com.noveogroup.modulotech.di.deviceDetailsModule
import com.noveogroup.modulotech.di.devicesListModule
import com.noveogroup.modulotech.di.resourcesModule
import com.noveogroup.modulotech.di.userProfileModule
import com.noveogroup.modulotech.rule.MainDispatcherRule
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule

class KoinModulesTest : KoinTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Test
    fun `Koin must provide all required dependencies`() {
        val modulesToCheck = listOf(
            resourcesModule,
            networkModule,
            databaseModule,
            synchronizationModule,
            userModule,
            devicesModule,
            devicesListModule,
            deviceDetailsModule,
            userProfileModule
        )
        checkKoinModules(modulesToCheck) {
            withInstance<Context>()
        }
    }
}