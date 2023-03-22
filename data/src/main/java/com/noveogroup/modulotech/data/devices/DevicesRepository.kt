package com.noveogroup.modulotech.data.devices

import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.domain.devices.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DevicesRepository(
    private val devicesDao: DevicesDao,
    private val deviceMapper: DeviceEntityMapper,
) : DevicesRepositoryApi {

    override fun observeDevices(): Flow<List<Device>> = devicesDao.observeDevices()
        .map { devices -> deviceMapper.mapToDomainModel(devices) }

    override suspend fun getDeviceById(id: String): Device = withContext(Dispatchers.Default) {
        val device = devicesDao.getDeviceById(id)
        deviceMapper.mapToDomainModel(device)
    }

    override suspend fun updateDevice(device: Device) {
        withContext(Dispatchers.Default) {
            devicesDao.update(deviceMapper.mapToDatabaseEntity(device))
        }
    }

    override suspend fun deleteDevice(id: String) {
        devicesDao.deleteDevice(id)
    }

}