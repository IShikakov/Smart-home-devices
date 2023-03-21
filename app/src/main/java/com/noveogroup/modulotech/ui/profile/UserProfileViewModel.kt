package com.noveogroup.modulotech.ui.profile

import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.common.DateMaskFormatter
import com.noveogroup.modulotech.domain.user.UserProfileInteractor
import com.noveogroup.modulotech.ui.base.BaseViewModel
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.profile.common.UserProfileMapper
import com.noveogroup.modulotech.ui.profile.model.UserProfileField
import com.noveogroup.modulotech.ui.profile.model.UserProfileScreenState
import com.noveogroup.modulotech.ui.profile.validation.UserProfileValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val mapper: UserProfileMapper,
    private val validator: UserProfileValidator,
    private val resourcesManager: ResourcesManager,
) : BaseViewModel() {

    private val dateMaskFormatter by lazy {
        DateMaskFormatter(resourcesManager.resolveString(R.string.birthdate_format))
    }

    private val _screenState = MutableStateFlow(UserProfileScreenState())
    val screenState: StateFlow<UserProfileScreenState> = _screenState

    init {
        viewModelScope.launch {
            val userProfile = userProfileInteractor.fetchUserProfile()
            _screenState.value = mapper.mapToScreenState(userProfile)
        }
    }

    fun fieldValueChanged(field: UserProfileField, value: String) {
        _screenState.update { state ->
            val formattedValue = when (field) {
                UserProfileField.BIRTHDATE -> dateMaskFormatter.format(value)
                else -> value
            }
            state.copy(
                userProfileFields = state.userProfileFields
                    .copyWithNewFieldValue(field, formattedValue)
            )
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            try {
                val userProfileFields = _screenState.value.userProfileFields
                val validationsErrors = validator.validate(userProfileFields)
                if (validationsErrors.isEmpty()) {
                    val newUserProfile = mapper.mapToUserProfile(userProfileFields)
                    userProfileInteractor.saveUserProfile(newUserProfile)
                    showMessage(resourcesManager.resolveString(R.string.Profile_saved_message))
                } else {
                    _screenState.update { state ->
                        state.copy(
                            userProfileFields = state.userProfileFields
                                .copyWithErrors(validationsErrors)
                        )
                    }
                }
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    private fun handleError(error: Throwable) {
        showMessage(
            resourcesManager.resolveString(
                R.string.error_message,
                error.javaClass.simpleName
            )
        )
    }

}