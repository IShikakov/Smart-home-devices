package com.noveogroup.modulotech.ui.profile

import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.common.formatters.DateMaskFormatter
import com.noveogroup.modulotech.domain.user.interactors.UserProfileInteractor
import com.noveogroup.modulotech.ui.base.BaseViewModel
import com.noveogroup.modulotech.ui.common.resources.ResourcesManager
import com.noveogroup.modulotech.ui.profile.common.UserProfileMapper
import com.noveogroup.modulotech.ui.profile.model.UserProfileField
import com.noveogroup.modulotech.ui.profile.model.UserProfileScreenState
import com.noveogroup.modulotech.ui.profile.validation.UserProfileValidator
import com.noveogroup.modulotech.ui.theme.darkTheme.DarkThemeDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val mapper: UserProfileMapper,
    private val validator: UserProfileValidator,
    private val resourcesManager: ResourcesManager,
    private val darkThemeDelegate: DarkThemeDelegate,
) : BaseViewModel() {

    private val dateMaskFormatter by lazy {
        DateMaskFormatter(resourcesManager.resolveString(R.string.Profile_birthdate_format))
    }

    private val _screenState = MutableStateFlow(UserProfileScreenState())
    val screenState: StateFlow<UserProfileScreenState> = _screenState
    val darkModeState: StateFlow<Boolean> = darkThemeDelegate.observeDarkThemeState()

    init {
        viewModelScope.launch {
            val userProfile = userProfileInteractor.fetchUserProfile()
            if (userProfile != null) {
                _screenState.value = mapper.mapToScreenState(userProfile)
            } else {
                showMessage(resourcesManager.resolveString(R.string.Profile_Error_profile_not_found))
            }
        }
    }

    fun fieldValueChanged(field: UserProfileField, value: String) {
        _screenState.update { state ->
            val formattedValue = when (field) {
                UserProfileField.Birthdate -> dateMaskFormatter.extractDateDigits(value)
                else -> value
            }
            state.copy(
                userProfileFields = state.userProfileFields
                    .copyWithNewFieldValue(field, formattedValue),
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
                                .copyWithErrors(validationsErrors),
                        )
                    }
                }
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    fun toggleDarkTheme() {
        darkThemeDelegate.toggleDarkTheme()
    }

    private fun handleError(error: Throwable) {
        showMessage(
            resourcesManager.resolveString(
                R.string.Common_error_message,
                error.javaClass.simpleName,
            ),
        )
    }
}
