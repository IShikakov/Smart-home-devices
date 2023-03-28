package com.noveogroup.modulotech.data.devices.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noveogroup.modulotech.data.devices.entity.DeviceEntity.Companion.TABLE_NAME
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

@Entity(tableName = TABLE_NAME)
internal data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_DEVICE_NAME)
    val deviceName: String,
    @ColumnInfo(name = COLUMN_DEVICE_TYPE)
    val type: DeviceType,
    @ColumnInfo(name = COLUMN_INTENSITY)
    val intensity: Int?,
    @ColumnInfo(name = COLUMN_MODE)
    val mode: DeviceMode?,
    @ColumnInfo(name = COLUMN_POSITION)
    val position: Int?,
    @ColumnInfo(name = COLUMN_TEMPERATURE)
    val temperature: Float?,
) {

    companion object {
        internal const val TABLE_NAME = "devices"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DEVICE_TYPE = "device_type"
        private const val COLUMN_DEVICE_NAME = "device_name"
        private const val COLUMN_INTENSITY = "intensity"
        private const val COLUMN_MODE = "mode"
        private const val COLUMN_POSITION = "position"
        private const val COLUMN_TEMPERATURE = "temperature"
    }
}
