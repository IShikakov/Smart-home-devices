package com.noveogroup.modulotech.ui.profile.validation

import com.noveogroup.modulotech.domain.common.formatters.DateMaskFormatter
import java.text.SimpleDateFormat
import java.util.*

sealed interface ValidationRule {

    fun isValid(value: String): Boolean

    object Mandatory : ValidationRule {
        override fun isValid(value: String): Boolean = value.isNotBlank()
    }

    data class DateFormat(val format: String) : ValidationRule {
        private val dateFormat by lazy { SimpleDateFormat(format, Locale.ENGLISH) }
        private val dateMaskFormatter by lazy { DateMaskFormatter(format) }

        override fun isValid(value: String): Boolean = try {
            val stringDate = dateMaskFormatter.format(value)
            dateFormat.parse(stringDate)?.let { dateFormat.format(it) == stringDate } ?: false
        } catch (e: Exception) {
            false
        }
    }
}