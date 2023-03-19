package com.noveogroup.modulotech.data.database.converters

import androidx.room.TypeConverter
import java.util.*

internal object DateConverter {
    @TypeConverter
    @JvmStatic
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun longToDate(millis: Long?): Date? = millis?.let { Date(it) }
}