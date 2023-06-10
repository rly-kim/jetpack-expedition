package com.example.jetpack_expedition.main.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.jetpack_expedition.SplashScreen
import com.example.jetpack_expedition.main.MainScreen2
import com.example.jetpack_expedition.main.MainViewModel
import com.example.jetpack_expedition.main.MainRouteItem
import com.example.jetpack_expedition.main.MainRoutePushItem
import com.example.jetpack_expedition.main.navigateSingleTopTo
import com.example.jetpack_expedition.main.screen.recent.RecentScreen
import com.example.jetpack_expedition.main.screen.recent.composable.ContactBottomSheetContent
import com.example.jetpack_expedition.main.screen.record.RecordListScreen
import com.example.jetpack_expedition.main.screen.settings.SettingsScreen
import com.example.jetpack_expedition.main.screen.settings.view.BlockManagementView
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

enum class RootNavigation(val route: String) {
    Root("root"),
    Main("main")
}

enum class RootRouteItem(val route: String) {
    Splash("splashScreen"),
    Main("mainScreen"),

}

@Composable
fun MainScreenNavigationConfigurations(
    navController: NavHostController,
) {
    NavHost(
        navController,
        route = RootNavigation.Root.route,
        startDestination = RootRouteItem.Splash.route,
    ) {
        composable(RootRouteItem.Splash.route) {
            SplashScreen {
                navController.navigate(RootRouteItem.Main.route)
            }
        }
        composable(RootRouteItem.Main.route) {
            MainScreen2(navController)
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainTabNavigator(
    navController: NavHostController,
    rootNavController: NavHostController,
    parentEntry: NavBackStackEntry,
) {
    NavHost(
        navController,
        route = RootNavigation.Main.route,
        startDestination = MainRouteItem.Recent.route,
    ) {
        composable(MainRouteItem.Recent.route) {
//            BackHandler(enabled = true) {
//                navController.popBackStack()
//                navController.navigate(RootNavigation.Main.route) {
//                    popUpTo(RootNavigation.Main.route) {
//                        inclusive = true
//                    }
//                }
//                rootNavController.navigate(RootRouteItem.Splash.route) {
//                    popUpTo(RootRouteItem.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            }
            RecentScreen(
                pullToRefreshUIViewModel = hiltViewModel(parentEntry),
                onBottomSheetCall = {
                    navController.navigate(MainRoutePushItem.ContactBottomSheet.route)
                }
            )
        }
        composable(MainRouteItem.Record.route) {
            RecordListScreen()
        }
        composable(MainRouteItem.Settings.route) {
            SettingsScreen(navigateToAdditionalFunctionsPage = {
                navController.navigate(
                    MainRoutePushItem.AdditionalFunctionsPage.route
                )
            })
        }

        composable(MainRoutePushItem.AdditionalFunctionsPage.route) {
            BlockManagementView(
                onPopStack = {
                    navController.popBackStack()
                }
            )
        }
        bottomSheet(route = MainRoutePushItem.ContactBottomSheet.route) {
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
    items: List<MainRouteItem>,
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
                      //  navController.navigateSingleTopTo(screen.route)
                        navController.navigate(screen.route)
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

@Composable
fun <T> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
