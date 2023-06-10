package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.jetpack_expedition.main.composable.MainTabNavigator
import com.example.jetpack_expedition.main.composable.PhoneAppBottomNavigation
import com.example.jetpack_expedition.main.composable.currentRoute
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen2(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val mainNavController = rememberNavController()
    val mainTabState = mainViewModel.mainTabState.collectAsStateWithLifecycle()
    val showBars = MainRouteItem.values().map { it.route }.contains(
        mainNavController
            .currentBackStackEntryAsState().value?.destination?.route
    )

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    mainNavController.navigatorProvider += bottomSheetNavigator

    Scaffold(
        topBar = {
            if (showBars)
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                    text = mainTabState.value.title,
                    style = MaterialTheme.typography.h3
                )
        },
        bottomBar = {
            Log.d("navigation route", "${currentRoute(mainNavController)}")
            if (showBars) {
                PhoneAppBottomNavigation(
                    mainViewModel,
                    mainNavController,
                    MainRouteItem.values().toList(),
                )
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                .background(MaterialTheme.colors.background)
                .padding(paddingValues = padding),
        ) {
            ModalBottomSheetLayout(bottomSheetNavigator) {
                MainTabNavigator(mainNavController, navController, navController.getBackStackEntry("splashScreen"))
            }
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
