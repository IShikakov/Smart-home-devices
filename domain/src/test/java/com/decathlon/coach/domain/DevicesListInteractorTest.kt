package com.decathlon.coach.domain

import com.noveogroup.modulotech.domain.devices.DevicesListInteractor
import com.noveogroup.modulotech.domain.devices.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.Device
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
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
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class DevicesListInteractorTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Mock
    private lateinit var devicesRepository: DevicesRepositoryApi
    private lateinit var interactor: DevicesListInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = DevicesListInteractor(devicesRepository)
    }

    @Test
    fun `Get all available filters`() = testScope.runTest {
        val filtersState = interactor.observeFilters().first()

        Assert.assertEquals(devicesFilters.size, filtersState.availableFilters.size)
        Assert.assertEquals(
            devicesFilters.sorted(),
            filtersState.availableFilters.toList().sorted()
        )
    }

    @Test
    fun `Get initial selected filters`() = testScope.runTest {
        val filtersState = interactor.observeFilters().first()

        Assert.assertEquals(devicesFilters.size, filtersState.selectedFilters.size)
        Assert.assertEquals(
            devicesFilters.sorted(),
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

        val expectedSelectedFilters = devicesFilters
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

        val expectedSelectedFilters = devicesFilters
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
        backgroundScope.launch {
            interactor.observeFilters().take(3).toList(filterStates)
        }
        interactor.toggleDevicesFilter(DeviceType.LIGHT)
        interactor.toggleDevicesFilter(DeviceType.LIGHT)

        Assert.assertEquals(3, filterStates.size)
        Assert.assertEquals(
            devicesFilters.sorted(),
            filterStates.last().selectedFilters.toList().sorted()
        )
    }

    @Test
    fun `Observe initial list of devices`() = testScope.runTest {
        `when`(devicesRepository.observeDevices())
            .thenReturn(MutableStateFlow(testDevicesList))

        val devices = interactor.observeDevices().first()

        Assert.assertEquals(testDevicesList.size, devices.size)
    }

    @Test
    fun `Observe filtered list of devices`() = testScope.runTest {
        `when`(devicesRepository.observeDevices())
            .thenReturn(MutableStateFlow(testDevicesList))

        val devices = mutableListOf<List<Device>>()
        backgroundScope.launch {
            interactor.observeDevices().take(2).toList(devices)
        }
        interactor.toggleDevicesFilter(DeviceType.LIGHT)

        Assert.assertEquals(2, devices.size)
        Assert.assertTrue(devices.first().size > devices.last().size)
        Assert.assertTrue(devices.first().any { device -> device.deviceType == DeviceType.LIGHT })
        Assert.assertTrue(devices.last().none { device -> device.deviceType == DeviceType.LIGHT })
    }

    @Test
    fun `Delete device`() = testScope.runTest {
        val devicesFlow = MutableStateFlow(testDevicesList)
        `when`(devicesRepository.observeDevices())
            .thenReturn(devicesFlow)
        `when`(devicesRepository.deleteDevice(anyString()))
            .then {
                val idToDelete = it.getArgument(0, String::class.java)
                devicesFlow.value = devicesFlow.value.filter { device -> device.id != idToDelete }
                Any()
            }

        val devices = mutableListOf<List<Device>>()
        backgroundScope.launch {
            interactor.observeDevices().take(2).toList(devices)
        }
        interactor.deleteDevice("0")

        verify(devicesRepository).deleteDevice(anyString())
        Assert.assertEquals(2, devices.size)
        Assert.assertTrue(devices.first().size > devices.last().size)
        Assert.assertTrue(devices.first().any { device -> device.id == "0" })
        Assert.assertTrue(devices.last().none { device -> device.id == "0" })
    }

    companion object {
        private val devicesFilters = DeviceType.values().toList()
        private val testDevicesList = listOf(
            Device.Light(
                id = "0",
                name = "Light",
                mode = DeviceMode.ON,
                intensity = 10
            ),
            Device.Heater(
                id = "1",
                name = "Heater",
                mode = DeviceMode.ON,
                temperature = 20
            ),
            Device.RollerShutter(
                id = "2",
                name = "Roller Shutter",
                position = 25
            )
        )
    }
}