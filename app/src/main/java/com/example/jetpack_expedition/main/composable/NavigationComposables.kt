package com.example.jetpack_expedition.main.composable

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import androidx.navigation.plusAssign
import com.example.jetpack_expedition.SplashScreen
import com.example.jetpack_expedition.main.MainScreen2
import com.example.jetpack_expedition.main.MainRouteItem
import com.example.jetpack_expedition.main.MainRoutePushItem
import com.example.jetpack_expedition.main.screen.recent.RecentScreen
import com.example.jetpack_expedition.main.screen.recent.composable.ContactBottomSheetContent
import com.example.jetpack_expedition.main.screen.record.RecordListScreen
import com.example.jetpack_expedition.main.screen.settings.SettingsScreen
import com.example.jetpack_expedition.main.screen.settings.view.BlockManagementView
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

enum class RootRouteItem(val route: String) {
    Root("root"),
    Splash("splashScreen"),
    Main("mainScreen"),
    Recent("recent"),
    Record("record"),
    Settings("settings"),

}

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreenNavigationConfigurations(
    navController: NavHostController,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    NavHost(
        navController,
        route = RootRouteItem.Root.route,
        startDestination = RootRouteItem.Splash.route,
    ) {
        composable(RootRouteItem.Splash.route) {
            SplashScreen {
                navController.navigate(RootRouteItem.Main.route) {
                    popUpTo(RootRouteItem.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }
        navigation(
            route = RootRouteItem.Main.route,
            startDestination = RootRouteItem.Recent.route,
        ) {
            composable(MainRouteItem.Recent.route) {
               // val parentEntry = navController.getBackStackEntry("splashScreen")
                MainScreen2(navController = navController, bottomSheetNavigator = bottomSheetNavigator) {
                    RecentScreen(
                        pullToRefreshUIViewModel = hiltViewModel(),//parentEntry),
                        onBottomSheetCall = {
                            navController.navigate(MainRoutePushItem.ContactBottomSheet.route)
                        }
                    )
                }
            }
            composable(RootRouteItem.Record.route) {
                MainScreen2(navController = navController, bottomSheetNavigator = bottomSheetNavigator) {
                    RecordListScreen()
                }
            }
            composable(RootRouteItem.Settings.route) {
                MainScreen2(navController = navController, bottomSheetNavigator = bottomSheetNavigator) {
                    SettingsScreen(navigateToAdditionalFunctionsPage = {
                        navController.navigate(
                            MainRoutePushItem.AdditionalFunctionsPage.route
                        )
                    })
                }
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

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
