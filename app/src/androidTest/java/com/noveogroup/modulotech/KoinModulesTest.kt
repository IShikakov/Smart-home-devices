package com.noveogroup.modulotech

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.noveogroup.modulotech.data.di.databaseModule
import com.noveogroup.modulotech.data.di.devicesModule
import com.noveogroup.modulotech.data.di.networkModule
import com.noveogroup.modulotech.data.di.synchronizationModule
import com.noveogroup.modulotech.data.di.userModule
import com.noveogroup.modulotech.di.darkThemeModule
import com.noveogroup.modulotech.di.deviceDetailsModule
import com.noveogroup.modulotech.di.devicesListModule
import com.noveogroup.modulotech.di.resourcesModule
import com.noveogroup.modulotech.di.userProfileModule
import com.noveogroup.modulotech.rule.MainDispatcherRule
import io.mockk.mockkClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule

@RunWith(AndroidJUnit4::class)
class KoinModulesTest {

    private lateinit var testContext: Context

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        testContext = ContextThemeWrapper(targetContext, R.style.Theme_ModuloTech)
    }

    @Test
    fun Koin_must_provide_all_required_dependencies() {
        koinApplication {
            androidContext(testContext)
            modules(
                listOf(
                    resourcesModule,
                    darkThemeModule,
                    networkModule,
                    databaseModule,
                    synchronizationModule,
                    userModule,
                    devicesModule,
                    devicesListModule,
                    deviceDetailsModule,
                    userProfileModule,
                ),
            )
        }.checkModules()
    }
}
