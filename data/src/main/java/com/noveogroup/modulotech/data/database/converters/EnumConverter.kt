package com.noveogroup.modulotech.data.database.converters

import androidx.room.TypeConverter
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

internal object EnumConverter {

    @TypeConverter
    @JvmStatic
    fun stringToDeviceType(string: String?): DeviceType? = string.convertToEnum<DeviceType>()

    @TypeConverter
    @JvmStatic
    fun deviceTypeToString(type: DeviceType?): String? = type.convertToString()

    @TypeConverter
    @JvmStatic
    fun stringToDeviceMode(string: String?): DeviceMode? = string.convertToEnum<DeviceMode>()

    @TypeConverter
    @JvmStatic
    fun deviceModeToString(mode: DeviceMode?): String? = mode.convertToString()

    private inline fun <reified T : Enum<T>> T?.convertToString(): String? = this?.name

    private inline fun <reified T : Enum<T>> String?.convertToEnum(): T? =
        this?.let { enumValueOf<T>(it) }
}