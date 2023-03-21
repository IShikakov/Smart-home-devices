package com.noveogroup.modulotech.ui.profile.model

import androidx.annotation.DrawableRes
import com.noveogroup.modulotech.R

data class UserProfileScreenState(
    @DrawableRes val userPhoto: Int = R.drawable.ic_user_photo,
    val userProfileFields: UserProfileFields = UserProfileFields(),
)