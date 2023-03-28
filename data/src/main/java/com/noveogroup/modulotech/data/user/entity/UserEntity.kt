package com.noveogroup.modulotech.data.user.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noveogroup.modulotech.data.user.entity.UserEntity.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
internal data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,
    @ColumnInfo(name = COLUMN_FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = COLUMN_LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = COLUMN_BIRTHDATE)
    val birthdate: Date,
    @Embedded(prefix = ADDRESS_PREFIX)
    val address: AddressEntity,
) {

    companion object {
        internal const val TABLE_NAME = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_BIRTHDATE = "birthdate"
        private const val ADDRESS_PREFIX = "address_"
    }
}
