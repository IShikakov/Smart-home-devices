package com.noveogroup.modulotech.ui.profile.model

data class UserProfileFields(
    val firstName: Field = Field(UserProfileField.FirstName),
    val lastName: Field = Field(UserProfileField.LastName),
    val birthdate: Field = Field(UserProfileField.Birthdate),
    val city: Field = Field(UserProfileField.City),
    val postalCode: Field = Field(UserProfileField.PostalCode),
    val street: Field = Field(UserProfileField.Street),
    val streetCode: Field = Field(UserProfileField.StreetCode),
    val country: Field = Field(UserProfileField.Country),
) {

    val userSectionFields = listOf(firstName, lastName, birthdate)
    val userAddressSectionFields = listOf(city, postalCode, street, streetCode, country)

    fun copyWithNewFieldValue(
        field: UserProfileField,
        newValue: String,
    ): UserProfileFields = when (field) {
        UserProfileField.City -> copy(city = Field(field = field, value = newValue))
        UserProfileField.Street -> copy(street = Field(field = field, value = newValue))
        UserProfileField.Country -> copy(country = Field(field = field, value = newValue))
        UserProfileField.LastName -> copy(lastName = Field(field = field, value = newValue))
        UserProfileField.Birthdate -> copy(birthdate = Field(field = field, value = newValue))
        UserProfileField.FirstName -> copy(firstName = Field(field = field, value = newValue))
        UserProfileField.PostalCode -> copy(postalCode = Field(field = field, value = newValue))
        UserProfileField.StreetCode -> copy(streetCode = Field(field = field, value = newValue))
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
