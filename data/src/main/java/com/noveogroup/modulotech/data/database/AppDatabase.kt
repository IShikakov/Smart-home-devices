package com.noveogroup.modulotech.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noveogroup.modulotech.data.database.AppDatabase.Companion.LATEST_VERSION
import com.noveogroup.modulotech.data.database.converters.DateConverter
import com.noveogroup.modulotech.data.database.converters.EnumConverter
import com.noveogroup.modulotech.data.devices.dao.DevicesDao
import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.data.user.entity.UserEntity

@Database(
    version = LATEST_VERSION,
    entities = [
        UserEntity::class,
        DeviceEntity::class,
    ],
)
@TypeConverters(
    DateConverter::class,
    EnumConverter::class,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun devicesDao(): DevicesDao
    abstract fun userDao(): UserDao

    companion object {
        internal const val LATEST_VERSION = 1
    }
}
