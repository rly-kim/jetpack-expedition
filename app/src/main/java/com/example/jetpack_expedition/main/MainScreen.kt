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
import com.example.jetpack_expedition.main.composable.MainScreenNavigationConfigurations
import com.example.jetpack_expedition.main.composable.PhoneAppBottomNavigation
import com.example.jetpack_expedition.main.composable.currentRoute
import com.example.jetpack_expedition.main.screen.recent.composable.ContactBottomSheetContent
import com.example.jetpack_expedition.main.screen.recent.RecentScreen
import com.example.jetpack_expedition.main.screen.record.RecordListScreen
import com.example.jetpack_expedition.main.screen.record.viewmodel.RecordDataViewModel
import com.example.jetpack_expedition.main.screen.settings.SettingsScreen
import com.example.jetpack_expedition.main.screen.settings.view.BlockManagementView
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
                text = mainTabState.value.title,
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
