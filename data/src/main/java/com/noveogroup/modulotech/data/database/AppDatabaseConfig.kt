package com.noveogroup.modulotech.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object AppDatabaseConfig {

    const val LATEST_VERSION = 1

    sealed class DCMigration(
        startVersion: Int,
        endVersion: Int,
        private val body: SupportSQLiteDatabase.() -> Unit,
    ) : Migration(startVersion, endVersion) {

        override fun migrate(database: SupportSQLiteDatabase) {
            body(database)
        }

        /* Write your migrations here */

    }
}