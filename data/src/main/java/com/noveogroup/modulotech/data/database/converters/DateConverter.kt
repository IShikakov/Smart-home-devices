package com.noveogroup.modulotech.data.database.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

internal object DateConverter {

    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"
    private const val UTC_TIME_ZONE = "UTC"
    private val dateFormat by lazy {
        SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE)
        }
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: Date?): String? = date?.let { dateFormat.format(it) }

    @TypeConverter
    @JvmStatic
    fun stringToDate(string: String?): Date? = string?.let { dateFormat.parse(it) }
}
