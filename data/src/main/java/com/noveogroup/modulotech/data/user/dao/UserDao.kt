package com.noveogroup.modulotech.data.user.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.noveogroup.modulotech.data.database.dao.BaseDao
import com.noveogroup.modulotech.data.user.entity.UserEntity

@Dao
internal abstract class UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} LIMIT 1")
    abstract suspend fun selectUser(): UserEntity

    @Query("SELECT EXISTS (SELECT 1 FROM ${UserEntity.TABLE_NAME} LIMIT 1)")
    abstract suspend fun isUserCreated(): Boolean

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    abstract suspend fun deleteUser()

    @Transaction
    open suspend fun refreshUser(user: UserEntity) {
        deleteUser()
        insert(user)
    }
}