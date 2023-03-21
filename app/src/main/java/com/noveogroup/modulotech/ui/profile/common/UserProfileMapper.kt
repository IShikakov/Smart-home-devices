package com.noveogroup.modulotech.ui.profile.common

import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.user.model.UserAddress
import com.noveogroup.modulotech.domain.user.model.UserProfile
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.profile.model.UserProfileField
import com.noveogroup.modulotech.ui.profile.model.UserProfileFields
import com.noveogroup.modulotech.ui.profile.model.UserProfileScreenState
import java.text.SimpleDateFormat
import java.util.*

class UserProfileMapper(
    resourcesManager: ResourcesManager,
) {

    private val dateFormat by lazy {
        SimpleDateFormat(
            resourcesManager.resolveString(R.string.birthdate_format),
            Locale.ENGLISH
        )
    }

    fun mapToScreenState(userProfile: UserProfile): UserProfileScreenState = with(userProfile) {
        UserProfileScreenState(
            userProfileFields = UserProfileFields(
                firstName = UserProfileFields.Field(
                    field = UserProfileField.FIRST_NAME,
                    value = firstName
                ),
                lastName = UserProfileFields.Field(
                    field = UserProfileField.LAST_NAME,
                    value = lastName
                ),
                birthdate = UserProfileFields.Field(
                    field = UserProfileField.BIRTHDATE,
                    value = dateFormat.format(birthdate)
                ),
                city = UserProfileFields.Field(
                    field = UserProfileField.CITY,
                    value = address.city
                ),
                postalCode = UserProfileFields.Field(
                    field = UserProfileField.POSTAL_CODE,
                    value = address.postalCode
                ),
                street = UserProfileFields.Field(
                    field = UserProfileField.STREET,
                    value = address.street
                ),
                streetCode = UserProfileFields.Field(
                    field = UserProfileField.STREET_CODE,
                    value = address.streetCode
                ),
                country = UserProfileFields.Field(
                    field = UserProfileField.COUNTRY,
                    value = address.country
                ),
            )
        )
    }

    fun mapToUserProfile(fields: UserProfileFields): UserProfile = with(fields) {
        UserProfile(
            firstName = firstName.value,
            lastName = lastName.value,
            birthdate = dateFormat.parse(birthdate.value) ?: Date(),
            address = UserAddress(
                city = city.value,
                postalCode = postalCode.value,
                street = street.value,
                streetCode = streetCode.value,
                country = country.value,
            )
        )
    }
}