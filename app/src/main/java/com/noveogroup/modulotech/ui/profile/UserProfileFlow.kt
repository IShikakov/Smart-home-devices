package com.noveogroup.modulotech.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noveogroup.modulotech.ui.navigation.Screen

@Composable
fun UserProfileFlow(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.UserProfileScreen.route
    ) {
        composable(Screen.UserProfileScreen.route) {
            UserProfileScreen(modifier)
        }
    }
}