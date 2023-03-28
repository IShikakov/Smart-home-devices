package com.decathlon.coach.domain

import com.noveogroup.modulotech.domain.devices.api.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.interactors.DevicesListInteractor
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.Heater
import com.noveogroup.modulotech.domain.devices.model.device.Light
import com.noveogroup.modulotech.domain.devices.model.device.RollerShutter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DevicesListInteractorTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @MockK
    private lateinit var devicesRepository: DevicesRepositoryApi
    private lateinit var interactor: DevicesListInteractor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        interactor = DevicesListInteractor(devicesRepository)
    }

    @Test
    fun `DevicesListInteractor must return initial filters when someone subscribes on it`() =
        testScope.runTest {
            val filtersState = interactor.observeFilters().first()

            Assert.assertEquals(devicesFilters.size, filtersState.availableFilters.size)
            Assert.assertEquals(
                devicesFilters.sorted(),
                filtersState.availableFilters.toList().sorted()
            )
        }

    @Test
    fun `DevicesListInteractor must return current selected filters when someone subscribes on it`() =
        testScope.runTest {
            val filtersState = interactor.observeFilters().first()

            Assert.assertEquals(devicesFilters.size, filtersState.selectedFilters.size)
            Assert.assertEquals(
                devicesFilters.sorted(),
                filtersState.selectedFilters.toList().sorted()
            )
        }

    @Test
    fun `DevicesListInteractor must remove a filter when it has been toggled once`() =
        testScope.runTest {
            val filterStates = mutableListOf<FiltersState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                interactor.observeFilters().take(2).toList(filterStates)
            }
            interactor.toggleDevicesFilter(DeviceType.Light)

            val expectedSelectedFilters = devicesFilters
                .minus(DeviceType.Light)
                .sorted()
            val actualSelectedFilters = filterStates.last()
                .selectedFilters
                .toList()
                .sorted()
            Assert.assertEquals(2, filterStates.size)
            Assert.assertEquals(expectedSelectedFilters, actualSelectedFilters)
        }

    @Test
    fun `DevicesListInteractor must remove two filters when each has been toggled once`() =
        testScope.runTest {
            val filterStates = mutableListOf<FiltersState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                interactor.observeFilters().take(3).toList(filterStates)
            }
            interactor.toggleDevicesFilter(DeviceType.Light)
            interactor.toggleDevicesFilter(DeviceType.RollerShutter)

            val expectedSelectedFilters = devicesFilters
                .minus(listOf(DeviceType.Light, DeviceType.RollerShutter))
                .sorted()
            val actualSelectedFilters = filterStates.last()
                .selectedFilters
                .toList()
                .sorted()
            Assert.assertEquals(3, filterStates.size)
            Assert.assertEquals(expectedSelectedFilters, actualSelectedFilters)
        }

    @Test
    fun `DevicesListInteractor must return the same filter list if a filter has been toggled twice`() =
        testScope.runTest {
            val filterStates = mutableListOf<FiltersState>()
            backgroundScope.launch {
                interactor.observeFilters().take(3).toList(filterStates)
            }
            interactor.toggleDevicesFilter(DeviceType.Light)
            interactor.toggleDevicesFilter(DeviceType.Light)

            Assert.assertEquals(3, filterStates.size)
            Assert.assertEquals(
                devicesFilters.sorted(),
                filterStates.last().selectedFilters.toList().sorted()
            )
        }

    @Test
    fun `DevicesListInteractor must return a list of devices when someone subscribes on it`() =
        testScope.runTest {
            every { devicesRepository.observeDevices() } returns MutableStateFlow(testDevicesList)

            val devices = interactor.observeDevices().first()

            Assert.assertEquals(testDevicesList.size, devices.size)
        }

    @Test
    fun `DevicesListInteractor must filter a list of devices if some filter has been toggled`() =
        testScope.runTest {
            every { devicesRepository.observeDevices() } returns MutableStateFlow(testDevicesList)

            val devices = mutableListOf<List<Device>>()
            backgroundScope.launch {
                interactor.observeDevices().take(2).toList(devices)
            }
            interactor.toggleDevicesFilter(DeviceType.Light)

            Assert.assertEquals(2, devices.size)
            Assert.assertTrue(devices.first().size > devices.last().size)
            Assert.assertTrue(
                devices.first().any { device -> device.deviceType == DeviceType.Light })
            Assert.assertTrue(
                devices.last().none { device -> device.deviceType == DeviceType.Light })
        }

    @Test
    fun `DevicesListInteractor must delete a device by its id`() = testScope.runTest {
        val devicesFlow = MutableStateFlow(testDevicesList)
        every { devicesRepository.observeDevices() } returns devicesFlow
        coEvery { devicesRepository.deleteDevice(any()) } answers {
            val idToDelete = this.firstArg() as String
            devicesFlow.value = devicesFlow.value.filter { device -> device.id != idToDelete }
        }

        val devices = mutableListOf<List<Device>>()
        backgroundScope.launch {
            interactor.observeDevices().take(2).toList(devices)
        }
        interactor.deleteDevice("0")

        coVerify { devicesRepository.deleteDevice(any()) }
        Assert.assertEquals(2, devices.size)
        Assert.assertTrue(devices.first().size > devices.last().size)
        Assert.assertTrue(devices.first().any { device -> device.id == "0" })
        Assert.assertTrue(devices.last().none { device -> device.id == "0" })
    }

    companion object {
        private val devicesFilters = DeviceType.values().toList()
        private val testDevicesList = listOf(
            Light(
                id = "0",
                name = "Light",
                mode = DeviceMode.ON,
                intensity = 10
            ),
            Heater(
                id = "1",
                name = "Heater",
                mode = DeviceMode.ON,
                temperature = 20.0f
            ),
            RollerShutter(
                id = "2",
                name = "Roller Shutter",
                position = 25
            )
        )
    }
}