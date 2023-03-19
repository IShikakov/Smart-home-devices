package com.noveogroup.modulotech.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

internal interface BaseDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<Entity>)

}