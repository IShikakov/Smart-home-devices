package com.noveogroup.modulotech.data.di

import androidx.room.Room
import com.noveogroup.modulotech.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().devicesDao() }

}

private const val DATABASE_NAME = "smart_home.db"