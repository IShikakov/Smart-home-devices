package com.noveogroup.modulotech.data.user

import com.noveogroup.modulotech.data.user.entity.AddressEntity
import com.noveogroup.modulotech.data.user.entity.UserEntity
import com.noveogroup.modulotech.domain.user.model.UserAddress
import com.noveogroup.modulotech.domain.user.model.UserProfile

internal class UserEntityMapper {

    fun mapToUserProfile(user: UserEntity): UserProfile = with(user) {
        UserProfile(
            firstName = firstName,
            lastName = lastName,
            birthdate = birthdate,
            address = with(address) {
                UserAddress(
                    city = city,
                    postalCode = postalCode,
                    street = street,
                    streetCode = streetCode,
                    country = country
                )
            }
        )
    }

    fun mapToUserEntity(user: UserProfile): UserEntity = with(user) {
        UserEntity(
            firstName = firstName,
            lastName = lastName,
            birthdate = birthdate,
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