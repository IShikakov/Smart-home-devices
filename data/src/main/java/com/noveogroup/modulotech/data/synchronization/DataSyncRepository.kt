package com.noveogroup.modulotech.data.synchronization

import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.data.network.api.ModuloApi
import com.noveogroup.modulotech.data.synchronization.mappers.DeviceResponseMapper
import com.noveogroup.modulotech.data.synchronization.mappers.UserResponseMapper
import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.domain.synchronization.api.DataSyncRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DataSyncRepository(
    private val api: ModuloApi,
    private val userDao: UserDao,
    private val devicesDao: DevicesDao,
) : DataSyncRepositoryApi {

    override suspend fun isSyncRequired(): Boolean = !userDao.isUserCreated()

    override suspend fun syncData() = withContext(Dispatchers.IO) {
        val response = api.fetchData()
        listOf(
            launch {
                val user = UserResponseMapper.mapToDatabaseEntity(response.user)
                userDao.refreshUser(user)
            },
            launch {
                val devices = response.devices.mapNotNull { deviceResponse ->
                    DeviceResponseMapper.mapToDatabaseEntity(deviceResponse)
                }
                devicesDao.refreshDevices(devices)
            },
        )
            .joinAll()
    }
}
