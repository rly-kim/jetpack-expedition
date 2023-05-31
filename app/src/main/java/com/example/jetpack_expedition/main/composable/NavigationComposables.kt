package com.example.jetpack_expedition.main.composable

import com.example.jetpack_expedition.main.screen.dialer.DialerScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpack_expedition.main.MainViewModel
import com.example.jetpack_expedition.main.ScreenTab
import com.example.jetpack_expedition.main.navigateSingleTopTo
import com.example.jetpack_expedition.main.screen.contact.ContactScreen
import com.example.jetpack_expedition.main.screen.recent.RecentScreen
import com.example.jetpack_expedition.main.screen.recent.composable.ContactBottomSheetContent
import com.example.jetpack_expedition.main.screen.record.RecordListScreen
import com.example.jetpack_expedition.main.screen.settings.SettingsScreen
import com.example.jetpack_expedition.main.screen.settings.view.BlockManagementView
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet


@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreenNavigationConfigurations(
    paddingValues: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        navController,
        startDestination = ScreenTab.Recent.route,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .background(Color.LightGray)
            .padding(paddingValues = paddingValues),
    ) {
        composable(ScreenTab.Dialer.route) {
            DialerScreen()
        }
        composable(ScreenTab.Recent.route) {
            RecentScreen(onBottomSheetCall = { navController.navigate("contactBottomSheet") } )
        }
        composable(ScreenTab.Contact.route) {
            ContactScreen()
        }
        composable(ScreenTab.Record.route) {
            RecordListScreen()
        }
        composable(ScreenTab.Settings.route) {
            SettingsScreen( navigateToAdditionalFunctionsPage = {navController.navigate("additionalFunctionsPage")})
        }
        composable("additionalFunctionsPage") {
            BlockManagementView(
                onPopStack = {
                    navController.popBackStack()
                }
            )
        }
        bottomSheet(route = "contactBottomSheet") {
            ContactBottomSheetContent({
                navController.popBackStack()
            }, {
                navController.popBackStack()
            })
        }

    }
}

@Composable
fun PhoneAppBottomNavigation(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    items: List<ScreenTab>,
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEachIndexed { index, screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.route) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        mainViewModel.tapped(index)
                        navController.navigateSingleTopTo(screen.route)
                    }
                },
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
