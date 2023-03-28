package com.noveogroup.modulotech.ui.profile.validation

import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.ui.common.resources.ResourcesManager
import com.noveogroup.modulotech.ui.profile.model.UserProfileField
import com.noveogroup.modulotech.ui.profile.model.UserProfileFields

class UserProfileValidator(
    private val resourcesManager: ResourcesManager,
) {

    private val fieldsValidationRules: Map<UserProfileField, List<ValidationRule>> by lazy {
        UserProfileField.values()
            .associateWith { field ->
                when (field) {
                    UserProfileField.City,
                    UserProfileField.Street,
                    UserProfileField.Country,
                    UserProfileField.LastName,
                    UserProfileField.FirstName,
                    UserProfileField.PostalCode,
                    UserProfileField.StreetCode,
                    -> listOf(ValidationRule.Mandatory)
                    UserProfileField.Birthdate -> listOf(
                        ValidationRule.Mandatory,
                        ValidationRule.DateFormat(resourcesManager.resolveString(R.string.Profile_birthdate_format)),
                    )
                }
            }
    }

    fun validate(fields: UserProfileFields): Map<UserProfileField, UserProfileFields.FieldError> {
        val validationErrors = mutableMapOf<UserProfileField, UserProfileFields.FieldError>()

        for ((field, value) in fields.userSectionFields + fields.userAddressSectionFields) {
            val validationRules = fieldsValidationRules[field] ?: continue

            validationRules.forEach { rule ->
                if (!rule.isValid(value)) {
                    validationErrors[field] = UserProfileFields.FieldError(rule.errorMessage)
                    return@forEach
                }
            }
        }

        return validationErrors
    }

    private val ValidationRule.errorMessage
        get() = when (this) {
            ValidationRule.Mandatory -> resourcesManager.resolveString(R.string.Profile_Error_empty_field)
            is ValidationRule.DateFormat -> resourcesManager.resolveString(
                R.string.Profile_Error_date_format,
                format,
            )
        }
}
