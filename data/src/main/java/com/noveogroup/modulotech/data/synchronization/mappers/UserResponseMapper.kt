package com.noveogroup.modulotech.data.synchronization.mappers

import com.noveogroup.modulotech.data.network.response.user.UserResponse
import com.noveogroup.modulotech.data.user.entity.AddressEntity
import com.noveogroup.modulotech.data.user.entity.UserEntity
import java.util.*

internal object UserResponseMapper {

    fun mapToDatabaseEntity(user: UserResponse): UserEntity = with(user) {
        UserEntity(
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            birthdate = Date(birthdate ?: 0),
            address = AddressEntity(
                city = address?.city ?: "",
                postalCode = address?.postalCode ?: "",
                street = address?.street ?: "",
                streetCode = address?.streetCode ?: "",
                country = address?.country ?: ""
            )
        )
    }
}