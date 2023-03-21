package com.noveogroup.modulotech.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

internal interface BaseDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<Entity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: Entity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entities: List<Entity>)

}