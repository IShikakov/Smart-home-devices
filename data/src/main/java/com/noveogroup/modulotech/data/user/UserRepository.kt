package com.noveogroup.modulotech.data.user

import com.noveogroup.modulotech.data.user.dao.UserDao
import com.noveogroup.modulotech.domain.user.UserRepositoryApi
import com.noveogroup.modulotech.domain.user.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserRepository(
    private val userDao: UserDao,
    private val userEntityMapper: UserEntityMapper,
) : UserRepositoryApi {

    override suspend fun fetchUserProfile(): UserProfile = withContext(Dispatchers.Default) {
        userEntityMapper.mapToUserProfile(userDao.selectUser())
    }

    override suspend fun saveUserProfile(userProfile: UserProfile) =
        withContext(Dispatchers.Default) {
            userDao.update(userEntityMapper.mapToUserEntity(userProfile))
        }

}