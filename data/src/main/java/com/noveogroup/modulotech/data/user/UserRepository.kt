package com.noveogroup.modulotech.data.user

import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.domain.user.UserRepositoryApi
import com.noveogroup.modulotech.domain.user.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserRepository(
    private val userDao: UserDao,
) : UserRepositoryApi {

    override suspend fun fetchUserProfile(): UserProfile = withContext(Dispatchers.IO) {
        UserEntityMapper.mapToUserProfile(userDao.selectUser())
    }

    override suspend fun saveUserProfile(userProfile: UserProfile) = withContext(Dispatchers.IO) {
        userDao.refreshUser(UserEntityMapper.mapToUserEntity(userProfile))
    }
}