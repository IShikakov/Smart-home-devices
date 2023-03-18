package com.decathlon.coach.domain.devices

import com.noveogroup.modulotech.domain.devices.DevicesInteractor
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DevicesInteractorTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var interactor: DevicesInteractor

    @Before
    fun setup() {
        interactor = DevicesInteractor()
    }

    @Test
    fun `Get all available filters`() = testScope.runTest {
        val filtersState = interactor.observeFilters().first()

        Assert.assertEquals(DeviceType.values().size, filtersState.availableFilters.size)
        Assert.assertEquals(
            DeviceType.values().toList().sorted(),
            filtersState.availableFilters.toList().sorted()
        )
    }

    @Test
    fun `Get initial selected filters`() = testScope.runTest {
        val filtersState = interactor.observeFilters().first()

        Assert.assertEquals(DeviceType.values().size, filtersState.selectedFilters.size)
        Assert.assertEquals(
            DeviceType.values().toList().sorted(),
            filtersState.selectedFilters.toList().sorted()
        )
    }

    @Test
    fun `Toggle one filter`() = testScope.runTest {
        val filterStates = mutableListOf<FiltersState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            interactor.observeFilters().take(2).toList(filterStates)
        }
        interactor.toggleDevicesFilter(DeviceType.LIGHT)

        val expectedSelectedFilters = DeviceType.values()
            .toList()
            .minus(DeviceType.LIGHT)
            .sorted()
        val actualSelectedFilters = filterStates.last()
            .selectedFilters
            .toList()
            .sorted()
        Assert.assertEquals(2, filterStates.size)
        Assert.assertEquals(expectedSelectedFilters, actualSelectedFilters)
    }

    @Test
    fun `Toggle two filters`() = testScope.runTest {
        val filterStates = mutableListOf<FiltersState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            interactor.observeFilters().take(3).toList(filterStates)
        }
        interactor.toggleDevicesFilter(DeviceType.LIGHT)
        interactor.toggleDevicesFilter(DeviceType.ROLLER_SHUTTER)

        val expectedSelectedFilters = DeviceType.values()
            .toList()
            .minus(listOf(DeviceType.LIGHT, DeviceType.ROLLER_SHUTTER))
            .sorted()
        val actualSelectedFilters = filterStates.last()
            .selectedFilters
            .toList()
            .sorted()
        Assert.assertEquals(3, filterStates.size)
        Assert.assertEquals(expectedSelectedFilters, actualSelectedFilters)
    }

    @Test
    fun `Toggle one filter twice`() = testScope.runTest {
        val filterStates = mutableListOf<FiltersState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            interactor.observeFilters().take(3).toList(filterStates)
        }
        interactor.toggleDevicesFilter(DeviceType.LIGHT)
        interactor.toggleDevicesFilter(DeviceType.LIGHT)

        Assert.assertEquals(3, filterStates.size)
        Assert.assertEquals(
            DeviceType.values().toList().sorted(),
            filterStates.last().selectedFilters.toList().sorted()
        )
    }
}