package com.noveogroup.modulotech.data.devices

import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.data.devices.mappers.DeviceEntityMapper
import com.noveogroup.modulotech.domain.devices.api.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.device.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DevicesRepository(
    private val devicesDao: DevicesDao,
) : DevicesRepositoryApi {

    override fun observeDevices(): Flow<List<Device>> = devicesDao.observeDevices()
        .map { devices ->
            devices.map { device -> DeviceEntityMapper.mapToDomainModel(device) }
        }

    override suspend fun getDeviceById(id: String): Device = withContext(Dispatchers.IO) {
        val device = devicesDao.getDeviceById(id)
        DeviceEntityMapper.mapToDomainModel(device)
    }

    override suspend fun updateDevice(device: Device) = withContext(Dispatchers.IO) {
        devicesDao.update(DeviceEntityMapper.mapToDatabaseEntity(device))
    }

    override suspend fun deleteDevice(id: String) {
        devicesDao.deleteDevice(id)
    }
}
