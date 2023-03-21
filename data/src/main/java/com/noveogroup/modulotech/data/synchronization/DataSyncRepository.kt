package com.noveogroup.modulotech.data.synchronization

import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.data.network.api.ModuloApi
import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.domain.synchronization.DataSyncRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DataSyncRepository(
    private val api: ModuloApi,
    private val userDao: UserDao,
    private val devicesDao: DevicesDao,
    private val userMapper: UserResponseMapper,
    private val deviceMapper: DeviceResponseMapper,
) : DataSyncRepositoryApi {

    override suspend fun isSyncRequired(): Boolean = !userDao.isUserCreated()

    override suspend fun syncData() {
        withContext(Dispatchers.Default) {
            val response = api.fetchData()
            listOf(
                launch {
                    val user = userMapper.mapToDatabaseEntity(response.user)
                    userDao.refreshUser(user)
                },
                launch {
                    val devices = deviceMapper.mapToDatabaseEntity(response.devices)
                    devicesDao.refreshDevices(devices)
                }
            )
                .joinAll()
        }
    }
}