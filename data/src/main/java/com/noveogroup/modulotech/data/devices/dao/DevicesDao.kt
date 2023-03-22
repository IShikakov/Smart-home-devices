package com.noveogroup.modulotech.data.devices.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.noveogroup.modulotech.data.database.dao.BaseDao
import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class DevicesDao : BaseDao<DeviceEntity> {

    @Query("SELECT * FROM ${DeviceEntity.TABLE_NAME}")
    abstract fun observeDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM ${DeviceEntity.TABLE_NAME} WHERE id = :id")
    abstract suspend fun getDeviceById(id: String): DeviceEntity

    @Query("DELETE FROM ${DeviceEntity.TABLE_NAME}")
    abstract suspend fun deleteDevices()

    @Query("DELETE FROM ${DeviceEntity.TABLE_NAME} WHERE id = :id")
    abstract suspend fun deleteDevice(id: String)

    @Transaction
    open suspend fun refreshDevices(devices: List<DeviceEntity>) {
        deleteDevices()
        insert(devices)
    }

}