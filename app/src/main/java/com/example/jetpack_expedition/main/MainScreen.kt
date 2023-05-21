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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.example.jetpack_expedition.main.recent.ui.ContactBottomSheetContent
import com.example.jetpack_expedition.main.recent.ui.RecentScreen
import com.example.jetpack_expedition.main.record.view.RecordListView
import com.example.jetpack_expedition.main.record.view.RecordingView
import com.example.jetpack_expedition.main.settings.SettingsScreen
import com.example.jetpack_expedition.main.settings.view.BlockManagementView
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

val mainViewModel = MainViewModel()
 @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
 @Composable
fun MainScreen2() {
     val mainTabState = mainViewModel.mainTabState.collectAsStateWithLifecycle()
     val navController = rememberNavController()
     val showBottomBar = ScreenTab.values().map {  it.route }.contains(
         navController
             .currentBackStackEntryAsState().value?.destination?.route
     )
     val bottomSheetNavigator = rememberBottomSheetNavigator()
     navController.navigatorProvider += bottomSheetNavigator

     Scaffold(
         topBar = {
             Text(
                 modifier = Modifier
                     .padding(top = 10.dp, bottom = 10.dp, start = 20.dp),
                 text = mainTabState.value.title, /// TODO 여기서 .value를 해야 옵저빙이 된다. 변수에서 value를 끌고 오면 옵저빙이 안 됨.. 뭐지
             )
         },
         bottomBar = {
             Log.d("navigation route", "${currentRoute(navController)}")
             if (showBottomBar) {
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

 @OptIn(ExperimentalMaterialNavigationApi::class)
 @Composable
private fun MainScreenNavigationConfigurations(
     paddingValues: PaddingValues,
    navController: NavHostController
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
                RecentScreen(navController)
            }
            composable(ScreenTab.Record.route) {
                RecordingView(navController)
            }
            composable(ScreenTab.Settings.route) {
                SettingsScreen(navController)
            }
            composable("부가기능") {
                BlockManagementView(navController)
            }
            composable("recordList") {
                RecordListView(navController)
            }
            bottomSheet(route = "contact") {
                ContactBottomSheetContent({}, {})
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
                label = { Text(screen.route ) },
                selected = currentRoute == screen.route,
                onClick = {
                    if(currentRoute != screen.route) {
                        mainViewModel.tapped(index)
                        navController.navigate(screen.route)
                    }
                },
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route // arguments.getString(KEY_ROUTE)
}

//sealed class BottomNavigationScreens(val route: String) {
//    object Recent: BottomNavigationScreens("최근기록")
//    object Record: BottomNavigationScreens("녹음")
//    object Settings: BottomNavigationScreens("설정")
//}



// val mainViewModel = MainViewModel()

//@Composable
//fun MainScreen2() {
//    val currentTabState = mainViewModel.mainTabState.collectAsState()
//
//    Scaffold(
//        bottomBar = {
//            SelectableBottomNavigation(
//                mainViewModel,
//                currentTabState,
//                ScreenTab.values(),
//            )
//        },
//    ) { padding ->
//        MainScreenNavigator(padding, currentTabState)
//    }
//}
//
//@Composable
//private fun SelectableBottomNavigation(
//    mainViewModel: MainViewModel,
//    currentTabState: State<ScreenTab>,
//    items: Array<ScreenTab>
//) {
//    BottomNavigation {
//        val currentRoute = currentTabState.value.route
//        items.forEachIndexed { index, screen ->
//            BottomNavigationItem(
//                icon = { screen.icon },
//                label = { Text(screen.route ) },
//                selected = currentRoute == screen.route,
//                onClick = {
//                    if(currentRoute != screen.route) {
//                        mainViewModel.tapped(index)
//                    }
//                },
//            )
//        }
//    }
//}
//
//@Composable
//private fun MainScreenNavigator(
//    padding: PaddingValues,
//    currentTabState: State<ScreenTab>,
//) {
//    Column(
//        modifier = Modifier
//            .padding(top = 30.dp)
//            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
//            .background(Color.LightGray)
//    ) {
//        Text(
//            modifier = Modifier
//                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp,),
//            text = currentTabState.value.title,
//        )
//        when(currentTabState.value) {
//            ScreenTab.Recent -> {
//                RecentScreen()
//            }
//            ScreenTab.Record -> {
//                RecordScreen()
//            }
//            ScreenTab.Settings -> {
//                SettingsScreen()
//            }
//        }
//    }
//}
