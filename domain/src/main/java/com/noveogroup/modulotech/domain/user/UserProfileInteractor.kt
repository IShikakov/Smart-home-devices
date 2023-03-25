package com.noveogroup.modulotech.domain.user

import com.noveogroup.modulotech.domain.user.model.UserProfile

class UserProfileInteractor(
    private val userRepository: UserRepositoryApi,
) {

    suspend fun fetchUserProfile(): UserProfile = userRepository.fetchUserProfile()

    suspend fun saveUserProfile(userProfile: UserProfile): Unit =
        userRepository.saveUserProfile(userProfile)
}