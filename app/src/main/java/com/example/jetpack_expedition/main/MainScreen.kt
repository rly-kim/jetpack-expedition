package com.example.jetpack_expedition.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_expedition.main.recent.ui.RecentScreen
import com.example.jetpack_expedition.main.record.RecordScreen
import com.example.jetpack_expedition.main.settings.SettingsScreen
import com.example.jetpack_expedition.main.settings.view.BlockManagementView

// https://proandroiddev.com/implement-bottom-bar-navigation-in-jetpack-compose-b530b1cd9ee2
 @Composable
fun MainScreen2() {
    // - 모든 컴포저블이 접근 가능한 위치에 생성
    // - NavController와 currentBackStackEntryAsState()를 통해
    // 제공하는 상태를 회면 외부에서 컴포저블 업데이트에 필요한 소스로 사용 가능
    // fragment에서 네비게이션 컴포넌트를 사용하는 경우에는 새로운
    // 네비게이션 그래프를 컴포즈에서 정의할 필요 없고!!
    // NavHost 컴포저블을 사용할 필요도 없다!!
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            PhoneAppBottomNavigation(
                navController,
                ScreenTab.values().toList(),
            )
        },
    ) { padding ->
        MainScreenNavigationConfigurations(padding, navController)
    }
}
 @Composable
private fun MainScreenNavigationConfigurations(
    padding: PaddingValues,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(top = 30.dp)
            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .background(Color.LightGray)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp,),
            text = "스크린",
        )
        NavHost(navController, startDestination = ScreenTab.Recent.route) {
            composable(ScreenTab.Recent.route) {
                RecentScreen(navController)
            }
            composable(ScreenTab.Record.route) {
                RecordScreen()
            }
            composable(ScreenTab.Settings.route) {
                SettingsScreen(navController)
            }
            composable("부가기능") {
                BlockManagementView(navController)
            }
        }
    }
}

@Composable
private fun PhoneAppBottomNavigation(
    navController: NavHostController,
    items: List<ScreenTab>,
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.route ) },
                selected = currentRoute == screen.route,
                onClick = {
                    if(currentRoute != screen.route) {
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
