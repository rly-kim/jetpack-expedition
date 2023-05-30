package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.jetpack_expedition.main.composable.MainScreenNavigationConfigurations
import com.example.jetpack_expedition.main.composable.PhoneAppBottomNavigation
import com.example.jetpack_expedition.main.composable.currentRoute
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen2(
    mainViewModel: MainViewModel = hiltViewModel(),
) {
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
            if (showBars)
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
            MainScreenNavigationConfigurations(padding, navController)
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
