package com.noveogroup.modulotech.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.noveogroup.modulotech.R

enum class SmartHomeTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String,
) {
    Devices(
        R.string.Main_devices_tab,
        R.drawable.ic_devices,
        "Devices",
    ),
    UserProfile(
        R.string.Main_user_profile_tab,
        R.drawable.ic_user_profile,
        "Profile",
    ),
}

sealed interface Screen {
    val route: String

    object DevicesListScreen : Screen {
        override val route: String = "devices"
    }

    object DeviceDetailsScreen : Screen {
        private const val ROUTE_PREFIX = "devices/device/"
        const val DEVICE_ID_KEY = "deviceId"

        override val route: String = "$ROUTE_PREFIX{$DEVICE_ID_KEY}"
        fun createRoute(deviceId: String): String = "$ROUTE_PREFIX$deviceId"
    }

    object UserProfileScreen : Screen {
        override val route: String = "user_profile"
    }
}
