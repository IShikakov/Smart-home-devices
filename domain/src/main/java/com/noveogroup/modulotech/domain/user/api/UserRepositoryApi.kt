package com.noveogroup.modulotech.domain.user.api

import com.noveogroup.modulotech.domain.user.model.UserProfile

interface UserRepositoryApi {
    suspend fun fetchUserProfile(): UserProfile
    suspend fun saveUserProfile(userProfile: UserProfile)
}