package com.noveogroup.modulotech.data.user

import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.data.user.mappers.UserEntityMapper
import com.noveogroup.modulotech.domain.user.api.UserRepositoryApi
import com.noveogroup.modulotech.domain.user.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserRepository(
    private val userDao: UserDao,
) : UserRepositoryApi {

    override suspend fun fetchUserProfile(): UserProfile? = withContext(Dispatchers.IO) {
        userDao.selectUser()?.let { UserEntityMapper.mapToUserProfile(it) }
    }

    override suspend fun saveUserProfile(userProfile: UserProfile) = withContext(Dispatchers.IO) {
        userDao.refreshUser(UserEntityMapper.mapToUserEntity(userProfile))
    }
}
