package com.noveogroup.modulotech.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noveogroup.modulotech.ui.common.views.DrawableIcon
import com.noveogroup.modulotech.ui.devices.DevicesFlow
import com.noveogroup.modulotech.ui.navigation.SmartHomeTab
import com.noveogroup.modulotech.ui.profile.UserProfileFlow
import com.noveogroup.modulotech.ui.theme.SmartHomeAppTheme
import com.noveogroup.modulotech.ui.theme.dark_theme.DarkThemeDelegate
import org.koin.androidx.compose.get

@Composable
fun SmartHomeApp(
    darkThemeDelegate: DarkThemeDelegate = get(),
) {
    val darkThemeEnabled by darkThemeDelegate.observeDarkThemeState().collectAsState()
    SmartHomeAppTheme(darkThemeEnabled = darkThemeEnabled) {
        SmartHomeAppContent()
    }
}

@Composable
private fun SmartHomeAppContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { SmartHomeBottomBar(navController) }
    ) { innerPaddingModifier ->
        TabNavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPaddingModifier)
        )
    }
}

@Composable
private fun SmartHomeBottomBar(
    navController: NavController,
) {
    val tabs = SmartHomeTab.values()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        tabs.forEach { tab ->
            BottomNavigationTab(
                tab = tab,
                isSelected = tab.route == currentRoute,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun TabNavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startTab: SmartHomeTab = SmartHomeTab.DEVICES,
) {
    NavHost(
        navController = navController,
        startDestination = startTab.route
    ) {
        composable(SmartHomeTab.DEVICES.route) { DevicesFlow(modifier) }
        composable(SmartHomeTab.USER_PROFILE.route) { UserProfileFlow(modifier) }
    }
}

@Composable
private fun RowScope.BottomNavigationTab(
    tab: SmartHomeTab,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = { DrawableIcon(tab.icon) },
        label = { Text(stringResource(tab.title)) },
        selected = isSelected,
        onClick = onClick,
        alwaysShowLabel = false,
        selectedContentColor = MaterialTheme.colors.secondary,
        unselectedContentColor = LocalContentColor.current,
        modifier = Modifier.navigationBarsPadding()
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_2, locale = "en")
@Composable
private fun SmartHomeAppContentPreview() {
    SmartHomeAppContent()
}
