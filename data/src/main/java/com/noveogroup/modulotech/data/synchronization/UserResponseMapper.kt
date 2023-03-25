package com.noveogroup.modulotech.data.synchronization

import com.noveogroup.modulotech.data.network.response.user.UserResponse
import com.noveogroup.modulotech.data.user.entity.AddressEntity
import com.noveogroup.modulotech.data.user.entity.UserEntity
import java.util.*

internal class UserResponseMapper {

    fun mapToDatabaseEntity(user: UserResponse): UserEntity = with(user) {
        UserEntity(
            firstName = firstName,
            lastName = lastName,
            birthdate = Date(birthdate),
            address = with(address) {
                AddressEntity(
                    city = city,
                    postalCode = postalCode,
                    street = street,
                    streetCode = streetCode,
                    country = country
                )
            }
        )
    }
}