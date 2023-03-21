package com.noveogroup.modulotech.ui.profile.model

data class UserProfileFields(
    val firstName: Field = Field(UserProfileField.FIRST_NAME),
    val lastName: Field = Field(UserProfileField.LAST_NAME),
    val birthdate: Field = Field(UserProfileField.BIRTHDATE),
    val city: Field = Field(UserProfileField.CITY),
    val postalCode: Field = Field(UserProfileField.POSTAL_CODE),
    val street: Field = Field(UserProfileField.STREET),
    val streetCode: Field = Field(UserProfileField.STREET_CODE),
    val country: Field = Field(UserProfileField.COUNTRY),
) {

    val userSectionFields = listOf(firstName, lastName, birthdate)
    val userAddressSectionFields = listOf(city, postalCode, street, streetCode, country)

    fun copyWithNewFieldValue(
        field: UserProfileField,
        newValue: String,
    ): UserProfileFields = when (field) {
        UserProfileField.CITY -> copy(city = Field(field = field, value = newValue))
        UserProfileField.STREET -> copy(street = Field(field = field, value = newValue))
        UserProfileField.COUNTRY -> copy(country = Field(field = field, value = newValue))
        UserProfileField.LAST_NAME -> copy(lastName = Field(field = field, value = newValue))
        UserProfileField.BIRTHDATE -> copy(birthdate = Field(field = field, value = newValue))
        UserProfileField.FIRST_NAME -> copy(firstName = Field(field = field, value = newValue))
        UserProfileField.POSTAL_CODE -> copy(postalCode = Field(field = field, value = newValue))
        UserProfileField.STREET_CODE -> copy(streetCode = Field(field = field, value = newValue))
    }

    fun copyWithErrors(errors: Map<UserProfileField, FieldError>): UserProfileFields =
        UserProfileFields(
            firstName = firstName.copy(error = errors[firstName.field]),
            lastName = lastName.copy(error = errors[lastName.field]),
            birthdate = birthdate.copy(error = errors[birthdate.field]),
            city = city.copy(error = errors[city.field]),
            postalCode = postalCode.copy(error = errors[postalCode.field]),
            street = street.copy(error = errors[street.field]),
            streetCode = streetCode.copy(error = errors[streetCode.field]),
            country = country.copy(error = errors[country.field]),
        )

    data class Field(
        val field: UserProfileField,
        val value: String = "",
        val error: FieldError? = null,
    )

    data class FieldError(val errorText: String)
}