package com.noveogroup.modulotech.data.devices

import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.domain.devices.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DevicesRepository(
    private val devicesDao: DevicesDao,
    private val deviceMapper: DeviceEntityMapper,
) : DevicesRepositoryApi {

    override fun observeDevices(): Flow<List<Device>> = devicesDao.observeDevices()
        .map { devices -> deviceMapper.mapToDomainModel(devices) }

    override suspend fun deleteDevice(id: String) {
        devicesDao.deleteDevice(id)
    }

}