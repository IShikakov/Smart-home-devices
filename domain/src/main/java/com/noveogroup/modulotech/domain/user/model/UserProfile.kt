package com.noveogroup.modulotech.domain.user.model

import java.util.Date

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val birthdate: Date,
    val address: UserAddress,
)
