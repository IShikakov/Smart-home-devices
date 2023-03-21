package com.noveogroup.modulotech.ui.profile.validation

import java.text.SimpleDateFormat
import java.util.*

sealed interface ValidationRule {

    fun isValid(value: String): Boolean

    object Mandatory : ValidationRule {
        override fun isValid(value: String): Boolean = value.isNotBlank()
    }

    data class DateFormat(val format: String) : ValidationRule {
        private val dateFormat by lazy { SimpleDateFormat(format, Locale.ENGLISH) }

        override fun isValid(value: String): Boolean = try {
            dateFormat.parse(value)?.let { dateFormat.format(it) == value } ?: false
        } catch (e: Exception) {
            false
        }
    }
}