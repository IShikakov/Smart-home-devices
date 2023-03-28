package com.noveogroup.modulotech.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

fun stringNavArg(key: String) = navArgument(key) { type = NavType.StringType }

fun NavBackStackEntry.getStringArg(key: String) = arguments?.getString(key)
    ?: throw IllegalArgumentException("$key not found in ${this.destination.route}!")
