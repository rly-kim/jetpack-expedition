package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.jetpack_expedition.main.recent.ui.ContactBottomSheetContent
import com.example.jetpack_expedition.main.recent.ui.RecentScreen
import com.example.jetpack_expedition.main.record.RecordListScreen
import com.example.jetpack_expedition.main.record.viewmodel.RecordDataViewModel
import com.example.jetpack_expedition.main.settings.SettingsScreen
import com.example.jetpack_expedition.main.settings.view.BlockManagementView
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen2(mainViewModel: MainViewModel, recordDataViewModel: RecordDataViewModel) {
    val mainTabState = mainViewModel.mainTabState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val showBars = ScreenTab.values().map { it.route }.contains(
        navController
            .currentBackStackEntryAsState().value?.destination?.route
    )
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    bottomSheetNavigator.navigatorSheetState.targetValue
    navController.navigatorProvider += bottomSheetNavigator

    Scaffold(
        topBar = {
            if(showBars)
                Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                text = mainTabState.value.title, /// TODO 여기서 .value를 해야 옵저빙이 된다. 변수에서 value를 끌고 오면 옵저빙이 안 됨.. 뭐지
            )
        },
        bottomBar = {
            Log.d("navigation route", "${currentRoute(navController)}")
            if (showBars) {
                PhoneAppBottomNavigation(
                    mainViewModel,
                    navController,
                    ScreenTab.values().toList(),
                )
            }
        },
    ) { padding ->
        com.google.accompanist.navigation.material.ModalBottomSheetLayout(bottomSheetNavigator) {
            MainScreenNavigationConfigurations(padding, navController, recordDataViewModel)
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun MainScreenNavigationConfigurations(
    paddingValues: PaddingValues,
    navController: NavHostController,
    recordDataViewModel: RecordDataViewModel,
) {
    NavHost(
        navController,
        startDestination = ScreenTab.Recent.route,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .background(Color.LightGray)
            .padding(paddingValues = paddingValues),
    ) {
        composable(ScreenTab.Recent.route) {
            RecentScreen(onBottomSheetCall = { navController.navigate("contactBottomSheet") } )
        }
        composable(ScreenTab.Record.route) {
            RecordListScreen(recordDataViewModel)
        }
        composable(ScreenTab.Settings.route) {
            SettingsScreen( navigateToAdditionalFunctionsPage = {navController.navigate("additionalFunctionsPage")})
        }
        composable("additionalFunctionsPage") {
            BlockManagementView()
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
private fun PhoneAppBottomNavigation(
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
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

private fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
