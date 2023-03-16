package com.noveogroup.modulotech.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.noveogroup.modulotech.R

object Routes {
    const val DEVICES_LIST = "devices_list"
    const val USER_PROFILE = "user_profile"
}

enum class SmartHomeTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    DEVICES(
        R.string.devices_tab,
        R.drawable.ic_devices,
        Routes.DEVICES_LIST
    ),
    USER_PROFILE(
        R.string.user_profile_tab,
        R.drawable.ic_user_profile,
        Routes.USER_PROFILE
    )
}