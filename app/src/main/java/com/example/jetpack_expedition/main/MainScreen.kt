package com.example.jetpack_expedition.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpack_expedition.main.composable.currentRoute
import com.example.jetpack_expedition.main.composable.navigateSingleTopTo
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen2(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    state: @Composable () -> Unit,
   //mainViewModel: MainViewModel = hiltViewModel(),
) {
//    val mainNavController = rememberNavController()
//    val mainTabState = mainViewModel.mainTabState.collectAsStateWithLifecycle()
    val showBars = MainRouteItem.values().map { it.route }.contains(
        //mainNavController
        navController
            .currentBackStackEntryAsState().value?.destination?.route
    )

    //val bottomSheetNavigator = rememberBottomSheetNavigator()
//    mainNavController.navigatorProvider += bottomSheetNavigator
//    navController.navigatorProvider += bottomSheetNavigator

    Scaffold(
        topBar = {
            if (showBars)
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                    //text = mainTabState.value.title,
                    text = "temp~",
                    style = MaterialTheme.typography.h3
                )
        },
        bottomBar = {
            Log.d("navigation route", "${currentRoute(navController)}")
            if (showBars) {
//                PhoneAppBottomNavigation(
//                    navController,
//                    MainRouteItem.values().toList(),
//                )
                BottomNavigation {
                    val currentRoute = currentRoute(navController)
                    MainRouteItem.values().toList().forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigateSingleTopTo(screen.route)
                                }
                            },
                        )
                    }
                }
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
                //MainTabNavigator(mainNavController, navController, navController.getBackStackEntry("splashScreen"))
                state()
            }
        }
    }
}
