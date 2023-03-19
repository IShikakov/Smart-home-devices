package com.noveogroup.modulotech.data.user.entity

import androidx.room.ColumnInfo

internal data class AddressEntity(
    @ColumnInfo(name = COLUMN_CITY)
    val city: String,
    @ColumnInfo(name = COLUMN_POSTAL_CODE)
    val postalCode: String,
    @ColumnInfo(name = COLUMN_STREET)
    val street: String,
    @ColumnInfo(name = COLUMN_STREET_CODE)
    val streetCode: String,
    @ColumnInfo(name = COLUMN_COUNTRY)
    val country: String,
) {

    companion object {
        private const val COLUMN_CITY = "city"
        private const val COLUMN_POSTAL_CODE = "postal_code"
        private const val COLUMN_STREET = "street"
        private const val COLUMN_STREET_CODE = "street_code"
        private const val COLUMN_COUNTRY = "country"
    }
}